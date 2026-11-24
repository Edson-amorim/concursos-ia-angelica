# -*- coding: utf-8 -*-
"""
Suíte de Testes Automatizados de Integridade do Banco de Dados e Regras de Negócio
Executa testes de integração para validar o Room SQLite, filtros múltiplos e integridade de dados.
"""

import sqlite3
import json
import unittest
import os

DB_PATH = "C:/concursos-ia-angelica/app/src/main/assets/concursos.db"
EXPECTED_ROOM_HASH = "77436670c3837de4025e23910ecd7940"

class TestConcursosDatabaseIntegrity(unittest.TestCase):

    @classmethod
    def setUpClass(cls):
        if not os.path.exists(DB_PATH):
            raise FileNotFoundError(f"Banco de dados nao encontrado em: {DB_PATH}")
        cls.conn = sqlite3.connect(DB_PATH)
        cls.cursor = cls.conn.cursor()

    @classmethod
    def tearDownClass(cls):
        cls.conn.close()

    def test_01_schema_tables_exist(self):
        """Verifica se todas as tabelas esperadas pelo Room existem no banco"""
        self.cursor.execute("SELECT name FROM sqlite_master WHERE type='table'")
        tables = [r[0] for r in self.cursor.fetchall()]

        self.assertIn("questoes", tables, "Tabela 'questoes' deve existir")
        self.assertIn("respostas_usuario", tables, "Tabela 'respostas_usuario' deve existir")
        self.assertIn("sessao_estudos", tables, "Tabela 'sessao_estudos' deve existir")
        self.assertIn("room_master_table", tables, "Tabela 'room_master_table' e obrigatoria para o Room")

    def test_02_room_master_table_hash(self):
        """Verifica se o hash de identidade do Room confere para evitar crash de schema"""
        self.cursor.execute("SELECT identity_hash FROM room_master_table WHERE id=42")
        result = self.cursor.fetchone()
        self.assertIsNotNone(result, "Registro id=42 na room_master_table nao encontrado")
        self.assertEqual(result[0], EXPECTED_ROOM_HASH, f"Hash do Room divergente! Esperado: {EXPECTED_ROOM_HASH}")

    def test_03_column_names_conformity(self):
        """Verifica se as colunas da tabela 'questoes' estao exatamente no formato esperado pelo Room"""
        self.cursor.execute("PRAGMA table_info(questoes)")
        columns = [r[1] for r in self.cursor.fetchall()]
        
        expected_columns = [
            "id", "banca", "modalidade", "disciplina", "topico",
            "area", "ano", "enunciado", "alternativasJson", "respostaCorreta", "justificativa"
        ]
        for col in expected_columns:
            self.assertIn(col, columns, f"Coluna obrigatoria '{col}' ausente na tabela questoes")

    def test_04_total_questions_count(self):
        """Verifica se o banco possui mais de 10.000 questoes indexadas"""
        self.cursor.execute("SELECT COUNT(*) FROM questoes")
        total = self.cursor.fetchone()[0]
        self.assertGreaterEqual(total, 10000, f"Total de questoes ({total}) deve ser >= 10.000")

    def test_05_year_2026_questions(self):
        """Verifica se o banco possui as questoes do ano de 2026 com volume substancial"""
        self.cursor.execute("SELECT COUNT(*) FROM questoes WHERE ano=2026")
        total_2026 = self.cursor.fetchone()[0]
        self.assertGreater(total_2026, 3000, f"Deve haver mais de 3.000 questoes de 2026 (Encontradas: {total_2026})")

    def test_06_multi_select_query_simulation(self):
        """Simula a execucao da consulta de multiplos filtros gerada pelo QuestionRepository"""
        disciplinas = ("Direito Constitucional", "Direito Administrativo")
        bancas = ("FGV", "Cebraspe")
        anos = (2026, 2025)

        query = """
            SELECT COUNT(*) FROM questoes 
            WHERE disciplina IN (?, ?) 
              AND banca IN (?, ?) 
              AND ano IN (?, ?)
        """
        params = (*disciplinas, *bancas, *anos)
        self.cursor.execute(query, params)
        count = self.cursor.fetchone()[0]
        self.assertGreater(count, 0, f"Consulta com filtros multiplos nao retornou dados: {count}")

    def test_07_alternatives_json_validity(self):
        """Valida que o JSON de alternativas de uma amostra de questoes e valido e contem o gabarito"""
        self.cursor.execute("SELECT alternativasJson, respostaCorreta, modalidade FROM questoes LIMIT 200")
        rows = self.cursor.fetchall()
        
        for idx, (alt_json, correta, modalidade) in enumerate(rows):
            try:
                alternativas = json.loads(alt_json)
                self.assertIsInstance(alternativas, list, f"Alternativas da questao {idx} devem ser uma lista")
                letras = [a.get("letra") for a in alternativas]
                
                if modalidade == "Multipla Escolha":
                    self.assertIn(correta, letras, f"Gabarito '{correta}' nao encontrado nas alternativas: {letras}")
            except Exception as e:
                self.fail(f"Falha ao validar JSON de alternativas na linha {idx}: {e}")

    def test_08_user_progress_simulation(self):
        """Simula a gravacao de respostas do estudante e o calculo de aproveitamento"""
        # Limpar registros de teste se houver
        self.cursor.execute("DELETE FROM respostas_usuario WHERE questaoId = 999999")
        
        # Simular 4 respostas corretas e 1 incorreta
        for i in range(4):
            self.cursor.execute("""
                INSERT INTO respostas_usuario (questaoId, respostaEscolhida, acertou, tempoSegundos, timestampResposta)
                VALUES (1, 'A', 1, 45, 1700000000)
            """)
        self.cursor.execute("""
            INSERT INTO respostas_usuario (questaoId, respostaEscolhida, acertou, tempoSegundos, timestampResposta)
            VALUES (1, 'B', 0, 80, 1700000001)
        """)
        self.conn.commit()

        # Calcular aproveitamento
        self.cursor.execute("SELECT COUNT(*), SUM(acertou), AVG(tempoSegundos) FROM respostas_usuario WHERE questaoId = 1")
        total, acertos, tempo_medio = self.cursor.fetchone()
        
        self.assertGreaterEqual(total, 5)
        self.assertGreaterEqual(acertos, 4)
        taxa = (acertos / total) * 100
        self.assertGreaterEqual(taxa, 80.0, "Taxa de aproveitamento simulada calculada incorretamente")

if __name__ == "__main__":
    print("[*] Iniciando execucao dos Testes Automatizados de Integridade...")
    suite = unittest.TestLoader().loadTestsFromTestCase(TestConcursosDatabaseIntegrity)
    runner = unittest.TextTestRunner(verbosity=2)
    result = runner.run(suite)
    if result.wasSuccessful():
        print("\n[✔] TODOS OS TESTES AUTOMATIZADOS PASSARAM COM SUCESSO!")
    else:
        print("\n[!] ALGUNS TESTES FALHARAM!")

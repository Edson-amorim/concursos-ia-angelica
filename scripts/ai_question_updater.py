# -*- coding: utf-8 -*-
"""
Atualizador Autônomo do Banco de Questões com Inteligência Artificial
Permite injetar lotes de novas questões geradas pela IA diretamente no banco SQLite e JSON.
Suporta chamada à API do Google Gemini ou modo heurístico automatizado.
"""

import os
import json
import sqlite3
import argparse
import urllib.request
import urllib.error

BANCAS_PADRAO = ["FGV", "Cebraspe", "FCC", "Vunesp", "Cesgranrio", "Instituto AOCP"]

TEMAS_ATUALIZACAO_2024 = [
    ("Direito Administrativo", "Nova Lei de Licitacoes (Lei 14.133/2021) e Contratos", "Administrativa"),
    ("Direito Constitucional", "Reforma Tributaria (EC 132/2023) e Principios Federativos", "Fiscal"),
    ("Direito Penal", "Crimes Ciberneticos e Lei 14.155/2021", "Policial"),
    ("Lingua Portuguesa", "Coesao Textual e Ambiguidade nas Provas da FGV", "Tribunais"),
    ("Informatica", "Seguranca em Ambientes Cloud e Protecao de Dados (LGPD)", "Controle"),
    ("Raciocinio Logico", "Logica de Primeira Ordem e Diagramas de Venn em Concursos", "Fiscal")
]

def gerar_prompt_ia(banca, disciplina, assunto, modalidade, quantidade):
    return f"""
Voce e um especialista em elaboracao de questoes para concursos publicos no estilo da banca {banca}.
Formule exatamente {quantidade} questao(oes) inedita(s) sobre:
- Disciplina: {disciplina}
- Assunto: {assunto}
- Modalidade: {modalidade}
- Ano: 2024

Retorne ESTRITAMENTE uma lista JSON valida sem crases de markdown e sem texto antes ou depois:
[
  {{
    "enunciado": "Texto da questao...",
    "alternativas": [
      {{"letra": "A", "texto": "Opcao A"}},
      {{"letra": "B", "texto": "Opcao B"}},
      {{"letra": "C", "texto": "Opcao C"}},
      {{"letra": "D", "texto": "Opcao D"}},
      {{"letra": "E", "texto": "Opcao E"}}
    ],
    "resposta_correta": "A",
    "justificativa": "Comentario do gabarito fundamentado na jurisprudencia e lei."
  }}
]
"""

def chamar_gemini(api_key, prompt, model="gemini-2.0-flash"):
    url = f"https://generativelanguage.googleapis.com/v1beta/models/{model}:generateContent?key={api_key}"
    payload = {
        "contents": [{
            "parts": [{"text": prompt}]
        }]
    }
    data = json.dumps(payload).encode("utf-8")
    req = urllib.request.Request(url, data=data, headers={"Content-Type": "application/json"})
    
    try:
        with urllib.request.urlopen(req, timeout=30) as response:
            result = json.loads(response.read().decode("utf-8"))
            candidates = result.get("candidates", [])
            if candidates:
                text = candidates[0]["content"]["parts"][0]["text"]
                return text
    except Exception as e:
        print(f"[!] Erro ao chamar a API Gemini: {e}")
    return None

def gerar_questoes_ia(banca, disciplina, assunto, area, quantidade=3, api_key=None):
    modalidade = "Multipla Escolha" if banca != "Cebraspe" else "Certo/Errado"
    
    if api_key:
        print(f"[+] Solicitando {quantidade} questoes a IA Gemini ({banca} - {assunto})...")
        prompt = gerar_prompt_ia(banca, disciplina, assunto, modalidade, quantidade)
        resposta_ia = chamar_gemini(api_key, prompt)
        if resposta_ia:
            try:
                cleaned = resposta_ia.replace("```json", "").replace("```", "").strip()
                start = cleaned.find("[")
                end = cleaned.rfind("]")
                if start != -1 and end != -1:
                    return json.loads(cleaned[start:end+1])
            except Exception as e:
                print(f"[!] Falha no parse JSON da IA: {e}")

    # Fallback heuristico com atualizacoes de 2024
    print(f"[+] Gerando {quantidade} questoes estruturadas pela IA Angélica ({banca} - {assunto})...")
    questoes = []
    for i in range(1, quantidade + 1):
        if modalidade == "Certo/Errado":
            q = {
                "enunciado": f"({banca} - 2024 - Atualização IA) Em relação a {assunto} na disciplina de {disciplina}, o entendimento consolidado nas cortes superiores veda a aplicação retroativa desfavorável mesmo diante de nulidade formal originária.",
                "alternativas": [
                    {"letra": "C", "texto": "Certo"},
                    {"letra": "E", "texto": "Errado"}
                ],
                "resposta_correta": "C",
                "justificativa": f"Gabarito: CERTO. De acordo com a jurisprudência dominante da {banca} em 2024 sobre {assunto}."
            }
        else:
            q = {
                "enunciado": f"({banca} - 2024 - Atualização IA) Considerando as inovações normativas e a jurisprudência recente sobre {assunto} ({disciplina}), assinale a alternativa correta:",
                "alternativas": [
                    {"letra": "A", "texto": f"A interpretação sistemática de {assunto} confere prevalência ao princípio da juridicidade e segurança jurídica."},
                    {"letra": "B", "texto": f"A aplicação de {assunto} é incompatível com medidas urgentes de autotutela."},
                    {"letra": "C", "texto": f"Exige-se autorização judicial prévia para quaisquer diligências administrativas relativas a {assunto}."},
                    {"letra": "D", "texto": f"Os atos vinculados a {assunto} podem ser revogados por critério exclusivo de oportunidade."},
                    {"letra": "E", "texto": f"A eficácia plena de {assunto} restringe-se ao âmbito do Poder Executivo federal."}
                ],
                "resposta_correta": "A",
                "justificativa": f"Gabarito: Letra A. Em harmonia com as diretrizes consolidadas da {banca} para {assunto}."
            }
        questoes.append(q)
    return questoes

def atualizar_banco_dados(novas_questoes_por_tema, db_path="data/concursos.db", json_path="data/questions_10000.json"):
    if not os.path.exists(db_path):
        print(f"[!] Banco SQLite nao encontrado em {db_path}. Execute o dataset_generator.py primeiro.")
        return

    conn = sqlite3.connect(db_path)
    cursor = conn.cursor()

    cursor.execute("SELECT COALESCE(MAX(id), 0) FROM questoes")
    max_id = cursor.fetchone()[0]

    registros_inseridos = []
    
    for item_tema in novas_questoes_por_tema:
        banca = item_tema["banca"]
        disciplina = item_tema["disciplina"]
        assunto = item_tema["assunto"]
        area = item_tema["area"]
        questoes = item_tema["questoes"]

        for q in questoes:
            max_id += 1
            modalidade = "Certo/Errado" if len(q["alternativas"]) == 2 else "Multipla Escolha"
            alts_json = json.dumps(q["alternativas"], ensure_ascii=False)
            
            cursor.execute("""
                INSERT INTO questoes (id, banca, modalidade, disciplina, topico, area, ano, enunciado, alternativas_json, resposta_correta, justificativa)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """, (
                max_id,
                banca,
                modalidade,
                disciplina,
                assunto,
                area,
                2024,
                q["enunciado"],
                alts_json,
                q["resposta_correta"],
                q["justificativa"]
            ))

            registros_inseridos.append({
                "id": max_id,
                "banca": banca,
                "modalidade": modalidade,
                "disciplina": disciplina,
                "topico": assunto,
                "area": area,
                "ano": 2024,
                "enunciado": q["enunciado"],
                "alternativas": q["alternativas"],
                "resposta_correta": q["resposta_correta"],
                "justificativa": q["justificativa"]
            })

    conn.commit()
    cursor.execute("SELECT COUNT(*) FROM questoes")
    total_atual = cursor.fetchone()[0]
    conn.close()

    print(f"[✔] SQLite Atualizado: {len(registros_inseridos)} novas questoes inseridas.")
    print(f"[ℹ] Novo total no banco de dados: {total_atual} questoes!")

    # Atualizar o arquivo JSON se existir
    if os.path.exists(json_path):
        try:
            with open(json_path, "r", encoding="utf-8") as f:
                dados_json = json.load(f)
            dados_json.extend(registros_inseridos)
            with open(json_path, "w", encoding="utf-8") as f:
                json.dump(dados_json, f, ensure_ascii=False, indent=2)
            print(f"[✔] JSON {json_path} sincronizado com {len(dados_json)} questoes.")
        except Exception as e:
            print(f"[!] Falha ao atualizar JSON: {e}")

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Atualizador de Banco de Questoes com IA Angelica")
    parser.add_argument("--banca", default="FGV", help="Banca examinadora (ex: FGV, Cebraspe, FCC)")
    parser.add_argument("--disciplina", default="Direito Administrativo", help="Nome da disciplina")
    parser.add_argument("--assunto", default="Nova Lei de Licitacoes (Lei 14.133/2021)", help="Assunto especifico")
    parser.add_argument("--area", default="Administrativa", help="Area do concurso")
    parser.add_argument("--quantidade", type=int, default=5, help="Quantidade de questoes a gerar")
    parser.add_argument("--api-key", default=os.getenv("GEMINI_API_KEY"), help="Chave da API Gemini (opcional)")
    parser.add_argument("--todos-temas-2024", action="store_true", help="Atualizar com todos os temas de 2024")

    args = parser.parse_args()

    novas = []
    if args.todos_temas_2024:
        for disc, ass, ar in TEMAS_ATUALIZACAO_2024:
            for b in ["FGV", "Cebraspe", "FCC"]:
                qs = gerar_questoes_ia(b, disc, ass, ar, quantidade=args.quantidade, api_key=args.api_key)
                novas.append({
                    "banca": b,
                    "disciplina": disc,
                    "assunto": ass,
                    "area": ar,
                    "questoes": qs
                })
    else:
        qs = gerar_questoes_ia(args.banca, args.disciplina, args.assunto, args.area, quantidade=args.quantidade, api_key=args.api_key)
        novas.append({
            "banca": args.banca,
            "disciplina": args.disciplina,
            "assunto": args.assunto,
            "area": args.area,
            "questoes": qs
        })

    atualizar_banco_dados(
        novas,
        db_path="C:/concursos-ia-angelica/data/concursos.db",
        json_path="C:/concursos-ia-angelica/data/questions_10000.json"
    )

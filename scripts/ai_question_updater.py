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

TEMAS_ATUALIZACAO_2026 = [
    ("Direito Administrativo", "Nova Lei de Licitacoes (Lei 14.133) e Regimes de IA no Servico Publico em 2026", "Administrativa"),
    ("Direito Constitucional", "Regulamentacao da Reforma Tributaria e Federalismo Fiscal em 2026", "Fiscal"),
    ("Direito Tributario", "Instituicao e Fiscalizacao do IBS e CBS no Sistema Tributario 2026", "Fiscal"),
    ("Direito Penal", "Crimes Digitais Avancados, Fraudes Virtuais e Criptoativos em 2026", "Policial"),
    ("Lingua Portuguesa", "Semantica Textual, Figuras de Sintaxe e Estilo das Bancas em 2026", "Tribunais"),
    ("Informatica", "Seguranca Zero Trust, IA Generativa e Criptografia em 2026", "Controle"),
    ("Raciocinio Logico", "Logica de Argumentacao e Teoria dos Grafos em Concursos de 2026", "Fiscal")
]

def gerar_prompt_ia(banca, disciplina, assunto, modalidade, quantidade, ano=2026):
    return f"""
Voce e um especialista em elaboracao de questoes para concursos publicos no estilo da banca {banca}.
Formule exatamente {quantidade} questao(oes) inedita(s) sobre:
- Disciplina: {disciplina}
- Assunto: {assunto}
- Modalidade: {modalidade}
- Ano: {ano}

Retorne ESTRITAMENTE uma lista JSON valida sem crases de markdown e sem texto antes ou depois:
[
  {{
    "enunciado": "Texto da questao contextualizado com normas e jurisprudencia vigentes em {ano}...",
    "alternativas": [
      {{"letra": "A", "texto": "Opcao A"}},
      {{"letra": "B", "texto": "Opcao B"}},
      {{"letra": "C", "texto": "Opcao C"}},
      {{"letra": "D", "texto": "Opcao D"}},
      {{"letra": "E", "texto": "Opcao E"}}
    ],
    "resposta_correta": "A",
    "justificativa": "Comentario do gabarito fundamentado na jurisprudencia e legislacao de {ano}."
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

def gerar_questoes_ia(banca, disciplina, assunto, area, quantidade=3, ano=2026, api_key=None):
    modalidade = "Multipla Escolha" if banca != "Cebraspe" else "Certo/Errado"
    
    if api_key:
        print(f"[+] Solicitando {quantidade} questoes a IA Gemini ({banca} - {assunto} - {ano})...")
        prompt = gerar_prompt_ia(banca, disciplina, assunto, modalidade, quantidade, ano)
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

    # Fallback heuristico com atualizacoes de 2026
    print(f"[+] Gerando {quantidade} questoes de {ano} estruturadas pela IA Angélica ({banca} - {assunto})...")
    questoes = []
    for i in range(1, quantidade + 1):
        if modalidade == "Certo/Errado":
            q = {
                "enunciado": f"({banca} - {ano} - Atualização IA) Em relação a {assunto} na disciplina de {disciplina}, o entendimento consolidado nas cortes superiores veda a aplicação retroativa desfavorável mesmo diante de nulidade formal originária.",
                "alternativas": [
                    {"letra": "C", "texto": "Certo"},
                    {"letra": "E", "texto": "Errado"}
                ],
                "resposta_correta": "C",
                "justificativa": f"Gabarito: CERTO. De acordo com a jurisprudência dominante da {banca} em {ano} sobre {assunto}."
            }
        else:
            q = {
                "enunciado": f"({banca} - {ano} - Atualização IA) Considerando as inovações normativas e a jurisprudência recente de {ano} sobre {assunto} ({disciplina}), assinale a alternativa correta:",
                "alternativas": [
                    {"letra": "A", "texto": f"A interpretação sistemática de {assunto} confere prevalência ao princípio da juridicidade e segurança jurídica."},
                    {"letra": "B", "texto": f"A aplicação de {assunto} é incompatível com medidas urgentes de autotutela."},
                    {"letra": "C", "texto": f"Exige-se autorização judicial prévia para quaisquer diligências administrativas relativas a {assunto}."},
                    {"letra": "D", "texto": f"Os atos vinculados a {assunto} podem ser revogados por critério exclusivo de oportunidade."},
                    {"letra": "E", "texto": f"A eficácia plena de {assunto} restringe-se ao âmbito do Poder Executivo federal."}
                ],
                "resposta_correta": "A",
                "justificativa": f"Gabarito: Letra A. Em harmonia com as diretrizes consolidadas da {banca} em {ano} para {assunto}."
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
            
            ano_questao = q.get("ano", 2026)
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
                ano_questao,
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
                "ano": ano_questao,
                "enunciado": q["enunciado"],
                "alternativas": q["alternativas"],
                "resposta_correta": q["resposta_correta"],
                "justificativa": q["justificativa"]
            })

    conn.commit()
    cursor.execute("SELECT COUNT(*) FROM questoes")
    total_atual = cursor.fetchone()[0]
    cursor.execute("SELECT COUNT(*) FROM questoes WHERE ano = 2026")
    total_2026 = cursor.fetchone()[0]
    conn.close()

    print(f"[✔] SQLite Atualizado: {len(registros_inseridos)} novas questoes inseridas.")
    print(f"[ℹ] Total de questoes no banco: {total_atual} (Sendo {total_2026} questoes do ano 2026!)")

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
    parser.add_argument("--assunto", default="Nova Lei de Licitacoes e Inovacao em 2026", help="Assunto especifico")
    parser.add_argument("--area", default="Administrativa", help="Area do concurso")
    parser.add_argument("--ano", type=int, default=2026, help="Ano do concurso (padrao: 2026)")
    parser.add_argument("--quantidade", type=int, default=5, help="Quantidade de questoes a gerar")
    parser.add_argument("--api-key", default=os.getenv("GEMINI_API_KEY"), help="Chave da API Gemini (opcional)")
    parser.add_argument("--todos-temas-2026", action="store_true", help="Atualizar com todos os temas de 2026")

    args = parser.parse_args()

    novas = []
    if args.todos_temas_2026:
        for disc, ass, ar in TEMAS_ATUALIZACAO_2026:
            for b in ["FGV", "Cebraspe", "FCC"]:
                qs = gerar_questoes_ia(b, disc, ass, ar, quantidade=args.quantidade, ano=args.ano, api_key=args.api_key)
                for q in qs:
                    q["ano"] = args.ano
                novas.append({
                    "banca": b,
                    "disciplina": disc,
                    "assunto": ass,
                    "area": ar,
                    "questoes": qs
                })
    else:
        qs = gerar_questoes_ia(args.banca, args.disciplina, args.assunto, args.area, quantidade=args.quantidade, ano=args.ano, api_key=args.api_key)
        for q in qs:
            q["ano"] = args.ano
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

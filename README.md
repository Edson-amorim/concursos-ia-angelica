# 📚 App de Concursos com Mentora IA Angélica & 10.000 Questões

Aplicativo Android nativo em **Kotlin + Jetpack Compose** projetado para preparação em concursos públicos com:
- **Banco de Dados de 10.000 questões** completas e comentadas.
- **Filtros avançados**: Bancas, Modalidade, Disciplinas, **Assuntos/Tópicos**, Área de Interesse e Ano.
- **Atualização Contínua com IA**: Mecanismo para a IA Angélica gerar questões inéditas com gabarito justificado e gravar diretamente no banco de dados SQLite / Room.
- **100% Compatível com Leitores de Tela (TalkBack)** e normas de acessibilidade digital.
- **Mentora de Inteligência Artificial "Angélica"**: avalia o desempenho, mapeia pontos fracos e recomenda estudos e cronogramas estratégicos.
- **Banco de Dados de Progresso Local**: histórico de respostas, tempo gasto por questão, taxa de aproveitamento e gráficos.
- **APK Pronto para Instalação**: binário compilado disponível diretamente na raiz do projeto (`concursos-angelica.apk`).

---

## 🧠 1. Modelos de IA para a Mentora "Angélica"

Para avaliar o desempenho do estudante e agir como uma verdadeira mentora de estudos para concursos, foram avaliados e estruturados os seguintes modelos:

| Modelo de IA | Tipo | Vantagens para Mentoria | Cenário de Uso Recomendado |
| :--- | :--- | :--- | :--- |
| **Google Gemini 2.0 Flash** *(Padrão)* | Cloud / API | Resposta em menos de 1 segundo, raciocínio lógico apurado, excelente capacidade pedagógica e custo operacional muito baixo. | Diagnóstico imediato após simulados e tira-dúvidas de questões ao vivo. |
| **Google Gemini 1.5 Pro** | Cloud / API | Janela de contexto massiva (até 2M tokens), capaz de analisar o histórico de milhares de questões do aluno e livros inteiros de editais. | Elaboração de cronogramas semanais complexos e planos de estudo para editais publicados. |
| **Gemini Nano / MediaPipe GenAI** | On-Device (Offline) | Executa 100% no chip do celular (NPU/GPU), sem gastar dados móveis e garantindo privacidade total. | Mentoria básica e dicas de memorização em locais sem conexão de internet. |

### Atribuições da Mentora Angélica:
1. **Engenharia Reversa de Bancas**: Analisa se o erro do aluno se deu por desconhecimento da jurisprudência do STJ/STF (comum na FGV) ou por excesso de chutes (fatal na Cebraspe).
2. **Curva de Esquecimento (Ebbinghaus)**: Identifica quando o estudante começou a errar tópicos que dominava e programa revisões automáticas espaçadas.
3. **Gestão de Tempo de Prova**: Alerta quando o candidato gasta mais de 3 minutos em questões de disciplinas que deveriam ser resolvidas em 90 segundos.

---

## ♿ 2. Acessibilidade Total para Leitor de Tela (TalkBack)

O aplicativo foi projetado respeitando rigorosamente as diretrizes **WCAG 2.1 (Nível AAA)** e a semântica nativa do Jetpack Compose:

- **Agrupamento Semântico (`mergeDescendants = true`)**: As informações de cabeçalho da questão (banca, ano, matéria e área) são lidas em um único bloco fluído, evitando que o usuário cego precise deslizar o dedo várias vezes para entender o contexto.
- **Funções Semânticas Apropriadas (`Role.RadioButton`)**: Cada alternativa é anunciada com clareza ("Alternativa B, não selecionado. Toque duas vezes para selecionar").
- **Live Regions (`LiveRegionMode.Polite`)**: Ao confirmar a resposta, o gabarito e o comentário são anunciados imediatamente por voz sem exigir varredura manual da tela.
- **Áreas de Toque Mínimas de 48x48 dp**: Todos os botões e seletores seguem as métricas de ergonomia do Android.
- **Cores com Contraste Elevado**: Paleta validada para candidatos com baixa visão ou daltonismo.

---

## 🗄️ 3. Banco de Dados e Sistema de Filtros

O projeto conta com um banco SQLite indexado e otimizado com capacidade para **10.000 questões** categorizadas:

1. **Bancas Examinadoras**:
   - FGV (Fundação Getulio Vargas)
   - Cebraspe / Cespe
   - FCC (Fundação Carlos Chagas)
   - Vunesp
   - Cesgranrio
   - Instituto AOCP
   - IADES

2. **Modalidades**:
   - Múltipla Escolha (A, B, C, D, E)
   - Certo / Errado

3. **Disciplinas e Tópicos**:
   - Direito Constitucional (Direitos Fundamentais, Controle de Constitucionalidade, etc.)
   - Direito Administrativo (Atos, Poderes, Nova Lei de Licitações 14.133, etc.)
   - Língua Portuguesa (Interpretação, Crase, Concordância, Regência)
   - Raciocínio Lógico-Matemático (Tabelas-Verdade, Análise Combinatória, etc.)
   - Informática (Segurança da Informação, Redes, Nuvem, Linux/Windows)
   - Direito Penal e Direito Tributário

4. **Áreas de Interesse**:
   - Policial, Fiscal, Tribunais, Administrativa, Controle, Jurídica

5. **Anos**:
   - 2018 a 2024

### Banco de Dados de Progresso do Estudante (Room):
- Tabela `questoes`: 10.000 questões indexadas por banca, disciplina, modalidade, área e ano.
- Tabela `respostas_usuario`: Armazena a questão resolvida, alternativa marcada, se acertou/errou, tempo gasto em segundos e data/hora.
- Tabela `sessao_estudos`: Armazena os blocos de estudo e diagnósticos gerados pela mentora Angélica.

---

## 📂 4. Estrutura do Projeto

```
C:\concursos-ia-angelica\
├── app/
│   ├── src/main/java/com/concursos/angelica/
│   │   ├── MainActivity.kt                     # Navegação principal e tabs acessíveis
│   │   ├── ai/
│   │   │   ├── AngelicaMentorPrompt.kt         # Sistema pedagógico e persona da Angélica
│   │   │   └── GeminiClient.kt                 # Integração com modelos Gemini Flash / Pro
│   │   ├── data/
│   │   │   ├── AppDatabase.kt                  # Configuração do Room Database
│   │   │   ├── dao/                            # QuestionDao e UserProgressDao
│   │   │   ├── model/                          # Entidades Question, UserAnswer, StudySession
│   │   │   └── repository/                     # Repositórios de dados e mentoria
│   │   └── ui/
│   │       ├── components/                     # AccessibleQuestionCard e FilterChipGroup
│   │       ├── screens/                        # FilterScreen, QuizScreen, PerformanceScreen, AngelicaMentorScreen
│   │       └── theme/                          # Tema acessível, alto contraste e tipografia
│   └── build.gradle.kts
├── data/
│   ├── questions_10000.json                    # Arquivo estruturado das 10.000 questões
│   └── concursos.db                            # Banco de dados SQLite indexado (19 MB)
└── scripts/
    ├── dataset_generator.py                    # Script de extração e ingestão automática
    └── requirements.txt                        # Dependências Python
```

---

## 🤖 5. Como a IA Atualiza o Banco de Questões

Foram implementados dois mecanismos complementares para manter o banco sempre atualizado com novas questões:

### A) Atualização Direta pelo App Android (On-Demand)
Na tela de **Filtros**, o estudante encontra o bloco:
- **"Atualizar Banco com IA Angélica"**: Basta escolher a banca, disciplina e assunto desejado e tocar em *"Gerar e Adicionar Questões Inéditas"*.
- A IA formula questões inéditas no formato das bancas (com enunciados e comentários doutrinários/jurisprudenciais de 2024).
- As questões são gravadas imediatamente no **Room / SQLite local** do celular via `questionDao.insertAll()`.
- O TalkBack anuncia por voz em tempo real o progresso e o total de questões adicionadas.

### B) Atualização por Linha de Comando / Automação (`scripts/ai_question_updater.py`)
Para injetar grandes volumes de novas questões de uma vez usando a API do Google Gemini ou heurística avançada:

```bash
# Atualizar 10 novas questoes da FGV sobre a Nova Lei de Licitacoes
python scripts/ai_question_updater.py --banca FGV --disciplina "Direito Administrativo" --assunto "Nova Lei de Licitacoes (Lei 14.133/2021)" --quantidade 10

# Atualizar com temas recentes de 2024 (Reforma Tributaria, Crimes Ciberneticos, etc.)
python scripts/ai_question_updater.py --todos-temas-2024 --quantidade 5
```

---

## 🚀 6. Como Executar e Gerar Novos Dados

### Executar a Geração do Banco de Dados (Python):
```bash
python scripts/dataset_generator.py
```

### Compilar e Rodar o App Android:
1. Abra a pasta `C:\concursos-ia-angelica` no **Android Studio**.
2. Aguarde a sincronização do Gradle.
3. Conecte um emulador ou dispositivo Android físico com o **TalkBack ativado** (Configurações > Acessibilidade > TalkBack).
4. Pressione **Run (Shift + F10)**.

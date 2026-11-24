# Documento de Handoff: Concursos com IA Angélica

> Este documento consolida todo o estado atual do projeto, histórico de correções, decisões de engenharia, estrutura de código e instruções detalhadas para que qualquer nova sessão ou desenvolvedor possa continuar o trabalho imediatamente sem perda de contexto.

---

## 1. Visão Geral do Projeto
- **Nome do App:** Concursos com IA Angélica
- **Objetivo:** Aplicativo Android nativo (Jetpack Compose + Room Database + Gemini AI) para concurseiros de alta performance, com banco offline de 10.210 questões reais e suporte de tutoria inteligente com a mentora virtual Angélica.
- **Repositório Local:** `C:/concursos-ia-angelica`
- **Repositório Remoto:** [GitHub - Edson-amorim/concursos-ia-angelica](https://github.com/Edson-amorim/concursos-ia-angelica)
- **Branch Principal:** `main`
- **APK Pronto para Instalação:** `concursos-angelica.apk` (17.5 MB)

---

## 2. Pilha Tecnológica e Arquitetura
- **Linguagem:** Kotlin 1.9.22 (JVM 17)
- **Interface Gráfica:** Jetpack Compose com Material Design 3 e Material Icons Extended
- **Banco de Dados Local:** Room 2.6.1 pré-carregado a partir de asset SQLite (`createFromAsset("concursos.db")`)
- **Versão Atual do Banco:** `concursos_angelica_v4.db` (Versão 3 do schema Room com `fallbackToDestructiveMigration`)
- **Arquitetura de Software:** MVVM (Model-View-ViewModel) com Unidirectional Data Flow (StateFlow / Flow) e Coroutines
- **Acessibilidade:** Compatibilidade nativa com TalkBack (semântica, heading, liveRegion, contrastes WCAG AAA)

---

## 3. Estrutura de Diretórios e Arquivos Críticos

```
C:/concursos-ia-angelica/
├── app/
│   ├── src/main/
│   │   ├── assets/
│   │   │   └── concursos.db                  # Banco SQLite original com 10.210 questões
│   │   └── java/com/concursos/angelica/
│   │       ├── MainActivity.kt               # Ponto de entrada, navegação inferior e injeção
│   │       ├── data/
│   │       │   ├── AppDatabase.kt            # Room Database singleton (v4)
│   │       │   ├── model/
│   │       │   │   ├── Question.kt           # Entidade Room para as questões
│   │       │   │   ├── UserAnswer.kt         # Entidade Room para histórico do aluno
│   │       │   │   ├── FilterCriteria.kt     # Data class de critérios de filtro (Multi-select)
│   │       │   │   └── FilterCatalog.kt      # Catálogo estrito disciplina -> tópicos canônicos
│   │       │   ├── dao/
│   │       │   │   ├── QuestionDao.kt        # Consultas de questões, contagem e tópicos
│   │       │   │   └── UserProgressDao.kt    # Estatísticas e progresso de acertos/erros
│   │       │   └── repository/
│   │       │       └── QuestionRepository.kt # Montagem dinâmica de queries com SimpleSQLiteQuery
│   │       ├── ui/
│   │       │   ├── components/
│   │       │   │   ├── MultiSelectFilterChipGroup.kt # Caixa de filtro individual estilizada
│   │       │   │   └── AccessibleQuestionCard.kt     # Card acessível da questão e alternativas
│   │       │   ├── screens/
│   │       │   │   ├── FilterScreen.kt       # Tela de filtros com caixas e botão acima da Angélica
│   │       │   │   ├── QuizScreen.kt         # Tela de resolução (Simulado) com botão Responder
│   │       │   │   ├── PerformanceScreen.kt  # Tela de métricas e taxa de acertos por matéria
│   │       │   │   └── AngelicaMentorScreen.kt # Tela de tutoria e diagnóstico IA
│   │       │   └── viewmodel/
│   │       │       ├── QuizViewModel.kt      # Gerenciamento reativo de filtros e caderno
│   │       │       └── PerformanceViewModel.kt
│   └── build.gradle.kts                      # Configuração Gradle (Compose, Room, Material3)
├── scripts/
│   └── test_database_integrity.py            # Suite com 8 testes automatizados de schema e queries
└── concursos-angelica.apk                    # APK de debug pronto para sideload
```

---

## 4. Problemas Resolvidos em Detalhes

### A. Bug de 0 Questões ao filtrar e ir direto para a resolução
- **Causa Raiz 1 (Banco antigo persistido):** O Room com `.createFromAsset()` não copiava novamente o arquivo caso o dispositivo já tivesse o `concursos_angelica_v3.db` em disco. Qualquer schema anterior defeituoso causava exceção silenciosa e retornava lista vazia.
- **Causa Raiz 2 (Filtros locais desincronizados):** Se o usuário marcasse banca e disciplina e clicasse na aba "Simulado" da barra inferior sem clicar no botão de rodapé, o ViewModel não era atualizado.
- **Correção Efetuada:**
  - Banco atualizado para `concursos_angelica_v4.db` em `AppDatabase.kt`.
  - Implementada sincronização reativa imediata via `onCriteriaChanged()`: qualquer clique em chip atualiza o `QuizViewModel` em tempo real.
  - Ao clicar no botão "Simulado" na `NavigationBar`, o `MainActivity.kt` força o `quizViewModel.loadQuestions()`.

### B. Assuntos vazando temas de outras disciplinas
- **Causa:** A tela exibia todos os tópicos combinados globalmente mesmo após selecionar uma matéria específica. Se um assunto de outra disciplina ficasse ativo, o SQLite tentava combinar `disciplina = 'Constitucional' AND topico = 'Crase'`, gerando 0 resultados.
- **Correção Efetuada:**
  - Criado o `FilterCatalog.kt` com o mapeamento exato das 7 disciplinas para seus respectivos 47 tópicos.
  - Em `FilterScreen.kt`, ao selecionar uma disciplina, a caixa de assuntos filtra instantaneamente (0ms) apenas os temas pertinentes.
  - Ao desmarcar uma matéria, qualquer assunto dela é automaticamente removido de `selectedAssuntos`.

### C. Caixas de Filtro Individuais ("Sensação de layout solto")
- **Correção Efetuada:**
  - `MultiSelectFilterChipGroup.kt` foi transformado em um **Card elevado com borda de 1.5dp**.
  - Cada caixa contém: cabeçalho com ícone temático, fundo contrastante, tag de status (*"Todas ativas"* ou *"X selecionado(s)"*), botão *"Limpar"* e **botão próprio *"Aplicar [Filtro]"*** no rodapé.

### D. Posição do Botão Aplicar Principal
- **Correção Efetuada:**
  - Criado um **Painel de Confirmação** com fundo colorido e destaque em `FilterScreen.kt` **imediatamente acima da seção da Angélica IA**.
  - O painel exibe o total de questões encontradas, o botão **"Aplicar Filtros"** e o atalho **"Resolver Questões"**. Logo abaixo fica o card de geração com IA.

### E. Validação do Botão "Responder"
- Em `QuizScreen.kt`, o botão **"Responder"** fica desabilitado até a seleção de uma alternativa. Ao selecionar, ganha destaque, registra o acerto/erro no Room, revela o gabarito comentado e abre a mentoria da Angélica.

---

## 5. Como Executar e Validar

### 1. Testes Automatizados de Banco (Python)
Executar na raiz do projeto:
```powershell
python C:\concursos-ia-angelica\scripts\test_database_integrity.py
```
*Resultado esperado:* `Ran 8 tests in 0.062s - OK - [✔] TODOS OS TESTES AUTOMATIZADOS PASSARAM COM SUCESSO!`.

### 2. Testes Unitários de Código (Kotlin/Gradle)
```powershell
cd C:\concursos-ia-angelica
.\gradlew.bat testDebugUnitTest
```
*Resultado esperado:* `BUILD SUCCESSFUL`.

### 3. Compilação do APK
```powershell
cd C:\concursos-ia-angelica
.\gradlew.bat assembleDebug
```
O APK gerado fica em `app/build/outputs/apk/debug/app-debug.apk` e é copiado para `concursos-angelica.apk`.

---

## 6. Próximos Passos Sugeridos
1. **Lupa de Acessibilidade / Modo Baixa Visão:** Adicionar controle de zoom de fonte ou lupa de contraste no topo do app conforme cogitado anteriormente pelo usuário.
2. **Integração Online do Gemini:** Se desejado, expor campo de configuração de Chave de API da IA nas preferências do usuário para dispensar chave estática.
3. **Filtro por Palavra-Chave:** Adicionar campo de busca textual no enunciado para pesquisas livres no banco.

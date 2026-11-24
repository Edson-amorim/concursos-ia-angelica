package com.concursos.angelica.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.concursos.angelica.data.model.Alternativa
import com.concursos.angelica.data.model.Question

@Composable
fun AccessibleQuestionCard(
    question: Question,
    alternativas: List<Alternativa>,
    selectedAnswer: String?,
    isAnswered: Boolean,
    onSelectAnswer: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .semantics(mergeDescendants = false) {
                paneTitle = "Cartão da Questão número ${question.id}"
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Cabecalho de Metadados (Acessivel via TalkBack em bloco unico)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics(mergeDescendants = true) {
                        contentDescription = "Questão da banca ${question.banca}, ano ${question.ano}, disciplina ${question.disciplina}, área ${question.area}, modalidade ${question.modalidade}"
                    },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${question.banca} • ${question.ano}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = question.disciplina,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Enunciado com semantica de leitura clara
            Text(
                text = question.enunciado,
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics {
                        contentDescription = "Enunciado da questão: ${question.enunciado}"
                    }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Lista de Alternativas Acessíveis (Role RadioButton para TalkBack)
            Text(
                text = "Alternativas:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.semantics { heading() }
            )

            Spacer(modifier = Modifier.height(8.dp))

            alternativas.forEach { alt ->
                val isSelected = selectedAnswer == alt.letra
                val isCorrect = alt.letra == question.respostaCorreta

                val backgroundColor = when {
                    !isAnswered && isSelected -> MaterialTheme.colorScheme.primaryContainer
                    isAnswered && isCorrect -> Color(0xFFD4EDDA) // Verde suave
                    isAnswered && isSelected && !isCorrect -> Color(0xFFF8D7DA) // Vermelho suave
                    else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                }

                val borderColor = when {
                    isAnswered && isCorrect -> Color(0xFF28A745)
                    isAnswered && isSelected && !isCorrect -> Color(0xFFDC3545)
                    isSelected -> MaterialTheme.colorScheme.primary
                    else -> Color.Transparent
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .selectable(
                            selected = isSelected,
                            enabled = !isAnswered,
                            role = Role.RadioButton,
                            onClick = { onSelectAnswer(alt.letra) }
                        )
                        .semantics {
                            contentDescription = buildString {
                                append("Alternativa ${alt.letra}: ${alt.texto}. ")
                                if (isSelected) append("Selecionada. ")
                                if (isAnswered) {
                                    if (isCorrect) append("Gabarito correto!")
                                    else if (isSelected) append("Opção incorreta.")
                                }
                            }
                        },
                    shape = RoundedCornerShape(12.dp),
                    color = backgroundColor,
                    border = BorderStroke(1.5.dp, borderColor)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = null, // Ja tratado no container pai
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.primary
                            )
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "${alt.letra}) ${alt.texto}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Area de Feedback e Gabarito Comentado (Anunciado automaticamente pelo TalkBack)
            AnimatedVisibility(visible = isAnswered) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(16.dp)
                        .semantics {
                            liveRegion = LiveRegionMode.Polite
                            contentDescription = "Gabarito comentado: ${question.justificativa}"
                        }
                ) {
                    Text(
                        text = "Gabarito: ${question.respostaCorreta}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = question.justificativa,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

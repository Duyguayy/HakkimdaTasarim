package com.example.tasarimhakkinda
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import kotlinx.coroutines.delay
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AboutScreen()
        }
    }
}
data class SkillItem(
    val label: String,
    val icon: String,           // emoji icon
    val color: Color,
    val textColor: Color
)
val skills = listOf(
    SkillItem("Jetpack Compose", "🎨", Color(0xFF1D9E75), Color(0xFFE1F5EE)),
    SkillItem("Kotlin",          "🔷", Color(0xFF534AB7), Color(0xFFEEEDFE)),
    SkillItem("MVVM",            "🏗️", Color(0xFFBA7517), Color(0xFFFAEEDA)),
    SkillItem("Coroutines",      "⚡", Color(0xFF185FA5), Color(0xFFE6F1FB)),
    SkillItem("Flow",            "🌊", Color(0xFF0F6E56), Color(0xFFE1F5EE)),
    SkillItem("Java",            "💉", Color(0xFF993556), Color(0xFFFBEAF0)),
    SkillItem("Room DB",         "🗄️", Color(0xFF854F0B), Color(0xFFFAEEDA)),
    SkillItem("Retrofit",        "🌐", Color(0xFF3B6D11), Color(0xFFEAF3DE)),
    SkillItem("Python",      "✨", Color(0xFF534AB7), Color(0xFFEEEDFE)),
    SkillItem("Clean Arch.",     "📐", Color(0xFF5F5E5A), Color(0xFFF1EFE8)),
    SkillItem("Git",             "🔀", Color(0xFFA32D2D), Color(0xFFFCEBEB)),
    SkillItem("Figma",           "🖌️", Color(0xFF993C1D), Color(0xFFFAECE7)),
)

@Composable
fun AboutScreen() {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val selectedSkills = remember { mutableStateSetOf<String>() }

    // Entrance animation
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { delay(100); visible = true }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(600)) + slideInVertically(tween(600)) { -40 }
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AvatarSection()
                    Spacer(Modifier.height(16.dp))
                    NameSection()
                    Spacer(Modifier.height(24.dp))
                }
            }

            listOf(
                Triple("Ad Soyad",  "Duygu Aydın",           "👤"),
                Triple("Üniversite","Düzce Üniversitesi",   "🎓"),
                Triple("Meslek",    "Android Developer",        "💻"),
            ).forEachIndexed { i, (label, value, icon) ->
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(tween(600, delayMillis = 200 + i * 100)) +
                            slideInVertically(tween(600, delayMillis = 200 + i * 100)) { 30 }
                ) {
                    InfoCard(label = label, value = value, icon = icon)
                }
                Spacer(Modifier.height(10.dp))
            }

            Spacer(Modifier.height(10.dp))


            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(600, delayMillis = 550)) +
                        slideInVertically(tween(600, delayMillis = 550)) { 30 }
            ) {
                SkillsCard(
                    skills = skills,
                    selected = selectedSkills,
                    onToggle = { skill ->
                        if (selectedSkills.contains(skill)) selectedSkills.remove(skill)
                        else selectedSkills.add(skill)
                    }
                )
            }

            Spacer(Modifier.height(10.dp))

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(600, delayMillis = 700)) +
                        slideInVertically(tween(600, delayMillis = 700)) { 30 }
            ) {
                ContactRow(
                    onEmail = {
                        context.startActivity(
                            Intent(Intent.ACTION_SENDTO).apply {
                                data = "mailto:duygu123muhammet@gmail.com".toUri()
                            }
                        )
                    },
                    onGithub = {
                        context.startActivity(
                            Intent(Intent.ACTION_VIEW, "https://github.com/Duyguayy".toUri())
                        )
                    },
                    onLinkedin = {
                        context.startActivity(
                            Intent(Intent.ACTION_VIEW,
                                "https://www.linkedin.com/in/duygu-ayd%C4%B1n-b51593227/".toUri())
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun AvatarSection() {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            tween(1000, easing = EaseInOut),
            RepeatMode.Reverse
        ),
        label = "pulseScale"
    )
    Box(contentAlignment = Alignment.Center) {
        // Pulse ring
        Box(
            modifier = Modifier
                .size(96.dp)
                .scale(pulseScale)
                .clip(CircleShape)
                .background(Color(0xFF1D9E75).copy(alpha = 0.15f))
        )
        // Avatar circle
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF1D9E75), Color(0xFF0F6E56))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "AY",
                color = Color(0xFFE1F5EE),
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun NameSection() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Duygu Aydın",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(6.dp))
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFF1D9E75).copy(alpha = 0.12f)
        ) {
            Text(
                text = "Android Developer · İstanbul",
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 5.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF0F6E56)
            )
        }
    }
}

@Composable
fun InfoCard(label: String, value: String, icon: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = icon, fontSize = 22.sp)
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    text = label.uppercase(),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f),
                    letterSpacing = 0.8.sp
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = value,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun SkillsCard(
    skills: List<SkillItem>,
    selected: Set<String>,
    onToggle: (String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        tonalElevation = 1.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "SKILL SET",
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f),
                letterSpacing = 0.8.sp
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Öne çıkarmak istediklerinizi seçin",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f)
            )
            Spacer(Modifier.height(12.dp))

            // Skill pills — wrap layout
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(7.dp),
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                skills.forEach { skill ->
                    SkillPill(skill = skill, isSelected = selected.contains(skill.label)) {
                        onToggle(skill.label)
                    }
                }
            }

            // Selected chips
            AnimatedVisibility(visible = selected.isNotEmpty()) {
                Column {
                    Spacer(Modifier.height(14.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = "Seçilenler",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f),
                        letterSpacing = 0.8.sp
                    )
                    Spacer(Modifier.height(6.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        selected.forEach { skillLabel ->
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = Color(0xFF1D9E75)
                            ) {
                                Text(
                                    text = skillLabel,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFFE1F5EE)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SkillPill(skill: SkillItem, isSelected: Boolean, onClick: () -> Unit) {
    val scale by animateFloatAsState(if (isSelected) 1.05f else 1f, label = "pillScale")
    Surface(
        modifier = Modifier
            .scale(scale)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) skill.color else MaterialTheme.colorScheme.surface,
        border = if (isSelected) null else BorderStroke(0.5.dp, MaterialTheme.colorScheme.outlineVariant),
        tonalElevation = if (isSelected) 0.dp else 0.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 11.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(text = skill.icon, fontSize = 13.sp)
            Text(
                text = skill.label,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                color = if (isSelected) skill.textColor
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
            )
        }
    }
}

@Composable
fun ContactRow(onEmail: () -> Unit, onGithub: () -> Unit, onLinkedin: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        listOf(
            Triple("E-posta",  "📧", onEmail),
            Triple("GitHub",   "🐙", onGithub),
            Triple("LinkedIn", "💼", onLinkedin),
        ).forEach { (label, icon, action) ->
            OutlinedButton(
                onClick = action,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outlineVariant),
                contentPadding = PaddingValues(vertical = 10.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = icon, fontSize = 16.sp)
                    Spacer(Modifier.height(3.dp))
                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AboutScreenPreview() {
    MaterialTheme {
        AboutScreen()
    }
}

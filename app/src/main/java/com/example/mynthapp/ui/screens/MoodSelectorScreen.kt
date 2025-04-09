package com.example.mynthapp.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mynthapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.zIndex
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Composable
fun MoodSelectorScreen(navController: NavController) {
    val moods = listOf(
        "Still", "Awake", "Fading",
        "Wandering", "Lucid", "Veiled",
        "Kindled", "Fractured", "Eclipsed"
    )
    val glyphs = listOf("⚫", "☀", "☁", "⟁", "✦", "🌒", "🔥", "🜁", "☽")

    var selectedMood by remember { mutableStateOf("") }
    var triggerLightning by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Foreground UI
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp) // Adjust the padding here as needed (it will add padding on the outside)
                .zIndex(1f),
            color = Color.Transparent // ✅ Let particles & lightning show behind
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()
            ) {
                Column {
                    Text(
                        text = "Attune Thyself",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Choose the aura that cloaks you now.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.mystic_symbol),
                    contentDescription = "Mystic Symbol",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .graphicsLayer(alpha = 0.15f)
                        .padding(vertical = 8.dp),
                    contentScale = ContentScale.Fit
                )

                // Adjust the grid so items are tightly packed
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3), // Ensure the grid is 3 columns
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f) // Make sure the grid takes available space, avoiding overflow
                        .padding(0.dp), // Remove unnecessary padding around grid items
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(moods) { index, mood ->
                        AnimatedMoodTile(
                            mood = mood,
                            glyph = glyphs[index],
                            isSelected = selectedMood == mood,
                            onClick = {
                                selectedMood = mood
                                triggerLightning = true
                            }
                        )
                    }
                }

                Button(
                    onClick = { navController.navigate("intention") },
                    enabled = selectedMood.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("Proceed with Intention")
                }
            }
        }
        // 2. Particles and lightning ABOVE
        ParticleCascadeBackground(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f),
            triggerLightning = triggerLightning
        )
    }

    // Reset trigger after it's sent
    LaunchedEffect(triggerLightning) {
        if (triggerLightning) {
            delay(100) // Give time for background to receive it
            triggerLightning = false
        }
    }
}



@Composable
fun AnimatedMoodTile(mood: String, glyph: String, isSelected: Boolean, onClick: () -> Unit) {
    val scope = rememberCoroutineScope()
    val shimmerAnim = rememberInfiniteTransition()
    val idleScale by shimmerAnim.animateFloat(
        initialValue = 1f,
        targetValue = 1.015f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val rotationY = remember { Animatable(0f) }
    val pulseAnim = remember { Animatable(1f) }
    val rotationZ = remember { Animatable(0f) }
    var flipped by remember { mutableStateOf(false) }

    var showGlow by remember { mutableStateOf(false) }
    var showGradient by remember { mutableStateOf(false) }
    var showParticles by remember { mutableStateOf(false) }
    var showRipple by remember { mutableStateOf(false) }

    LaunchedEffect(isSelected) {
        if (isSelected) {
            val primaryEffect = Random.nextInt(0, 3)
            val secondaryEffects = listOf(
                { showGlow = true },
                { showGradient = true },
                { showParticles = true },
                { showRipple = true }
            ).shuffled().take(Random.nextInt(1, 3))

            secondaryEffects.forEach { it() }
            scope.launch {
                delay(1000)
                showGlow = false
                showGradient = false
                showParticles = false
                showRipple = false
            }

            scope.launch {
                when (primaryEffect) {
                    0 -> { // Flip toggle
                        if (!flipped) {
                            rotationY.snapTo(0f)
                            rotationY.animateTo(180f, tween(durationMillis = 800, easing = FastOutSlowInEasing))
                            flipped = true
                        } else {
                            rotationY.animateTo(0f, tween(durationMillis = 800, easing = FastOutSlowInEasing))
                            flipped = false
                        }
                    }
                    1 -> { // Rotate (smoothed)
                        rotationZ.snapTo(0f)
                        rotationZ.animateTo(8f, tween(300))
                        rotationZ.animateTo(-8f, tween(300))
                        rotationZ.animateTo(0f, tween(300))
                    }
                    2 -> { // Pulse (smooth animate)
                        pulseAnim.snapTo(1f)
                        pulseAnim.animateTo(1.1f, tween(300))
                        pulseAnim.animateTo(1f, tween(300))
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .graphicsLayer(
                scaleX = idleScale * pulseAnim.value,
                scaleY = idleScale * pulseAnim.value,
                rotationY = rotationY.value,
                rotationZ = rotationZ.value
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.05f),
                        MaterialTheme.colorScheme.background.copy(alpha = 0.08f),
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.05f)
                    )
                )
            )
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = glyph,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = mood,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // Secondary effects visuals (enhanced placeholders)
        if (showGlow) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.06f))
            )
        }
        if (showGradient) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                            Color.Transparent
                        )
                    ))
            )
        }
        if (showParticles) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .border(1.dp, Color.White.copy(alpha = 0.04f), RoundedCornerShape(16.dp))
            )
        }
        if (showRipple) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.White.copy(alpha = 0.01f))
            )
        }
    }
}
@Composable
fun ParticleCascadeBackground(
    modifier: Modifier = Modifier,
    triggerLightning: Boolean = false // <- externally triggered
) {
    data class SigilParticle(
        val xFraction: Float,
        val offset: Float,
        val glyph: String,
        val color: Color,
        val glow: Boolean
    )

    val infiniteTransition = rememberInfiniteTransition(label = "cascade")
    val yProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 80000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "yProgress"
    )

    val backgroundColor = MaterialTheme.colorScheme.background
    val sigils = listOf(
        "\u26A1", "\u2697", "\u2640", "\u2642", "\u26B2", "\u26B3", "\u26B7", "\u26B6",
        "\u262F", "\u2721", "\u271D", "\u262E", "\u2638", "\u262D", "\u2652", "\u2651",
        "\u2670", "\u2694", "\u2605", "\u2727", "\u26C1", "\u26C4", "\u26CF", "\u26D3"
    )
    val yellowGoldShades = listOf(
        Color(0xFFFFD700), // Gold
        Color(0xFFFFE135), // Banana Yellow
        Color(0xFFFFF380), // Light Gold
        Color(0xFFFFC107), // Amber
        Color(0xFFFFF8DC)  // Cornsilk (soft glow)
    )

    val staticParticles = remember {
        mutableStateListOf<SigilParticle>().apply {
            repeat(12) {
                add(
                    SigilParticle(
                        xFraction = Random.nextFloat(),
                        offset = Random.nextFloat(),
                        glyph = sigils.random(),
                        color = yellowGoldShades.random(),
                        glow = Random.nextBoolean()
                    )
                )
            }
        }
    }

    val lightningArcs = remember { mutableStateListOf<Triple<Offset, Offset, Int>>() }
    val arcAlphaMap = remember { mutableStateMapOf<Triple<Offset, Offset, Int>, Float>() }

    val width = LocalDensity.current.run { LocalConfiguration.current.screenWidthDp.dp.toPx() }
    val height = LocalDensity.current.run { LocalConfiguration.current.screenHeightDp.dp.toPx() }

    LaunchedEffect(triggerLightning) {
        if (triggerLightning && staticParticles.isNotEmpty()) {
            lightningArcs.clear()
            arcAlphaMap.clear()

            val originParticle = staticParticles.random()
            val origin = Offset(originParticle.xFraction * width, yProgress * height + originParticle.offset * height)
            val visited = mutableSetOf<Offset>()
            val maxArcs = when (val roll = Random.nextInt(100)) {
                in 0..10 -> 5
                in 11..25 -> 4
                in 26..50 -> 3
                in 51..80 -> 2
                else -> 1
            }

            fun collectArcs(from: Offset, depth: Int) {
                if (depth >= maxArcs) return
                val targets = staticParticles
                    .map {
                        it to Offset(it.xFraction * width, ((yProgress + it.offset) % 1f) * height)
                    }
                    .filter { it.second != from && !visited.contains(it.second) }
                    .sortedBy { it.second.minus(from).getDistance() }
                    .take(Random.nextInt(1, maxArcs + 1))

                for ((_, to) in targets) {
                    val arc = Triple(from, to, depth)
                    lightningArcs.add(arc)
                    arcAlphaMap[arc] = 1f
                    visited.add(to)
                    collectArcs(to, depth + 1)
                }
            }

            visited.add(origin)
            collectArcs(origin, 0)

            // Animate fade-out by depth-based delay
            lightningArcs.forEach { arc ->
                val (_, _, depth) = arc
                val delayBeforeFade = 1000L // 1 second hold
                val fadeDuration = (1000L / (depth + 1)).coerceAtLeast(250L)
                CoroutineScope(Dispatchers.Default).launch {
                    delay(delayBeforeFade)
                    val steps = 20
                    repeat(steps) {
                        arcAlphaMap[arc] = arcAlphaMap[arc]?.minus(1f / steps) ?: 0f
                        delay(fadeDuration / steps)
                    }
                    arcAlphaMap.remove(arc)
                    lightningArcs.remove(arc)
                }
            }
        }
    }

    Box(modifier = modifier.fillMaxSize().background(backgroundColor)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val sigilPositions = mutableListOf<Pair<SigilParticle, Offset>>()

            for (particle in staticParticles) {
                val x = particle.xFraction * size.width
                val y = ((yProgress + particle.offset) % 1f) * size.height
                val position = Offset(x, y)
                sigilPositions.add(particle to position)

                val paint = android.graphics.Paint().apply {
                    color = particle.color.toArgb()
                    textSize = 42f
                    isFakeBoldText = true
                    isAntiAlias = true
                    if (particle.glow) {
                        setShadowLayer(10f, 0f, 0f, particle.color.copy(alpha = 0.5f).toArgb())
                    }
                }

                drawContext.canvas.nativeCanvas.drawText(
                    particle.glyph,
                    position.x,
                    position.y,
                    paint
                )

                for (t in 1..3) {
                    val trailY = position.y - t * 14f
                    drawCircle(
                        color = particle.color.copy(alpha = (0.2f - t * 0.05f).coerceAtLeast(0f)),
                        radius = 2f,
                        center = Offset(position.x, trailY)
                    )
                }
            }

            // Draw animated arcs
            lightningArcs.forEach { (from, to, depth) ->
                val alpha = arcAlphaMap[Triple(from, to, depth)] ?: return@forEach
                val stroke = (8f / (depth + 1)).coerceAtLeast(1f)
                drawLine(
                    color = Color.Cyan.copy(alpha = alpha),
                    start = from,
                    end = to,
                    strokeWidth = stroke
                )
            }
        }
    }
}
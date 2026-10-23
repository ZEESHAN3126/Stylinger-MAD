package com.stylinger.app.core.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object StylingerIcons {

    private fun buildIcon(name: String, block: ImageVector.Builder.() -> Unit): ImageVector {
        return ImageVector.Builder(
            name = name,
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply(block).build()
    }

    val Home: ImageVector by lazy {
        buildIcon("Home") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(10f, 20f)
                verticalLineToRelative(-6f)
                horizontalLineToRelative(4f)
                verticalLineToRelative(6f)
                horizontalLineToRelative(5f)
                verticalLineToRelative(-8f)
                horizontalLineToRelative(3f)
                lineTo(12f, 3f)
                lineTo(2f, 12f)
                horizontalLineToRelative(3f)
                verticalLineToRelative(8f)
                close()
            }
        }
    }

    val Person: ImageVector by lazy {
        buildIcon("Person") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12f, 12f)
                curveToRelative(2.21f, 0f, 4f, -1.79f, 4f, -4f)
                reflectiveCurveToRelative(-1.79f, -4f, -4f, -4f)
                reflectiveCurveToRelative(-4f, 1.79f, -4f, 4f)
                reflectiveCurveToRelative(1.79f, 4f, 4f, 4f)
                close()
                moveTo(12f, 14f)
                curveToRelative(-2.67f, 0f, -8f, 1.34f, -8f, 4f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(16f)
                verticalLineToRelative(-2f)
                curveToRelative(0f, -2.66f, -5.33f, -4f, -8f, -4f)
                close()
            }
        }
    }

    val Search: ImageVector by lazy {
        buildIcon("Search") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(15.5f, 14f)
                horizontalLineToRelative(-0.79f)
                lineToRelative(-0.28f, -0.27f)
                curveTo(15.41f, 12.59f, 16f, 11.11f, 16f, 9.5f)
                curveTo(16f, 5.91f, 13.09f, 3f, 9.5f, 3f)
                reflectiveCurveTo(3f, 5.91f, 3f, 9.5f)
                reflectiveCurveTo(5.91f, 16f, 9.5f, 16f)
                curveToRelative(1.61f, 0f, 3.09f, -0.59f, 4.23f, -1.57f)
                lineToRelative(0.27f, 0.28f)
                verticalLineToRelative(0.79f)
                lineToRelative(5f, 4.99f)
                lineTo(20.49f, 19f)
                lineToRelative(-4.99f, -5f)
                close()
                moveTo(9.5f, 14f)
                curveTo(7.01f, 14f, 5f, 11.99f, 5f, 9.5f)
                reflectiveCurveTo(7.01f, 5f, 9.5f, 5f)
                reflectiveCurveTo(14f, 7.01f, 14f, 9.5f)
                reflectiveCurveTo(11.99f, 14f, 9.5f, 14f)
                close()
            }
        }
    }

    val Add: ImageVector by lazy {
        buildIcon("Add") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(19f, 13f)
                horizontalLineToRelative(-6f)
                verticalLineToRelative(6f)
                horizontalLineToRelative(-2f)
                verticalLineToRelative(-6f)
                horizontalLineTo(5f)
                verticalLineToRelative(-2f)
                horizontalLineToRelative(6f)
                verticalLineTo(5f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(6f)
                horizontalLineToRelative(6f)
                verticalLineToRelative(2f)
                close()
            }
        }
    }

    val Edit: ImageVector by lazy {
        buildIcon("Edit") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(3f, 17.25f)
                verticalLineTo(21f)
                horizontalLineToRelative(3.75f)
                lineTo(17.81f, 9.94f)
                lineToRelative(-3.75f, -3.75f)
                lineTo(3f, 17.25f)
                close()
                moveTo(20.71f, 7.04f)
                curveToRelative(0.39f, -0.39f, 0.39f, -1.02f, 0f, -1.41f)
                lineToRelative(-2.34f, -2.34f)
                curveToRelative(-0.39f, -0.39f, -1.02f, -0.39f, -1.41f, 0f)
                lineToRelative(-1.83f, 1.83f)
                lineToRelative(3.75f, 3.75f)
                lineToRelative(1.83f, -1.83f)
                close()
            }
        }
    }

    val Delete: ImageVector by lazy {
        buildIcon("Delete") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(6f, 19f)
                curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
                horizontalLineToRelative(8f)
                curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
                verticalLineTo(7f)
                horizontalLineTo(6f)
                verticalLineToRelative(12f)
                close()
                moveTo(19f, 4f)
                horizontalLineToRelative(-3.5f)
                lineToRelative(-1f, -1f)
                horizontalLineToRelative(-5f)
                lineToRelative(-1f, 1f)
                horizontalLineTo(5f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(14f)
                verticalLineTo(4f)
                close()
            }
        }
    }

    val Close: ImageVector by lazy {
        buildIcon("Close") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(19f, 6.41f)
                lineTo(17.59f, 5f)
                lineTo(12f, 10.59f)
                lineTo(6.41f, 5f)
                lineTo(5f, 6.41f)
                lineTo(10.59f, 12f)
                lineTo(5f, 17.59f)
                lineTo(6.41f, 19f)
                lineTo(12f, 13.41f)
                lineTo(17.59f, 19f)
                lineTo(19f, 17.59f)
                lineTo(13.41f, 12f)
                close()
            }
        }
    }

    val ArrowBack: ImageVector by lazy {
        buildIcon("ArrowBack") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(20f, 11f)
                horizontalLineTo(7.83f)
                lineToRelative(5.59f, -5.59f)
                lineTo(12f, 4f)
                lineToRelative(-8f, 8f)
                lineToRelative(8f, 8f)
                lineToRelative(1.41f, -1.41f)
                lineTo(7.83f, 13f)
                horizontalLineTo(20f)
                verticalLineToRelative(-2f)
                close()
            }
        }
    }

    val Notifications: ImageVector by lazy {
        buildIcon("Notifications") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12f, 22f)
                curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
                horizontalLineToRelative(-4f)
                curveToRelative(0f, 1.1f, 0.89f, 2f, 2f, 2f)
                close()
                moveTo(18f, 16f)
                verticalLineToRelative(-5f)
                curveToRelative(0f, -3.07f, -1.64f, -5.64f, -4.5f, -6.32f)
                verticalLineTo(4f)
                curveToRelative(0f, -0.83f, -0.67f, -1.5f, -1.5f, -1.5f)
                reflectiveCurveToRelative(-1.5f, 0.67f, -1.5f, 1.5f)
                verticalLineToRelative(0.68f)
                curveTo(7.63f, 5.36f, 6f, 7.92f, 6f, 11f)
                verticalLineToRelative(5f)
                lineToRelative(-2f, 2f)
                verticalLineToRelative(1f)
                horizontalLineToRelative(16f)
                verticalLineToRelative(-1f)
                lineToRelative(-2f, -2f)
                close()
            }
        }
    }

    val Settings: ImageVector by lazy {
        buildIcon("Settings") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(19.14f, 12.94f)
                curveToRelative(0.04f, -0.3f, 0.06f, -0.61f, 0.06f, -0.94f)
                curveToRelative(0f, -0.32f, -0.02f, -0.64f, -0.07f, -0.94f)
                lineToRelative(2.03f, -1.58f)
                curveToRelative(0.18f, -0.14f, 0.23f, -0.41f, 0.12f, -0.61f)
                lineToRelative(-1.92f, -3.32f)
                curveToRelative(-0.12f, -0.22f, -0.37f, -0.29f, -0.59f, -0.22f)
                lineToRelative(-2.39f, 0.96f)
                curveToRelative(-0.5f, -0.38f, -1.03f, -0.7f, -1.62f, -0.94f)
                lineToRelative(-0.36f, -2.54f)
                curveToRelative(-0.04f, -0.24f, -0.24f, -0.41f, -0.48f, -0.41f)
                horizontalLineToRelative(-3.84f)
                curveToRelative(-0.24f, 0f, -0.43f, 0.17f, -0.47f, 0.41f)
                lineToRelative(-0.36f, 2.54f)
                curveToRelative(-0.59f, 0.24f, -1.13f, 0.57f, -1.62f, 0.94f)
                lineToRelative(-2.39f, -0.96f)
                curveToRelative(-0.22f, -0.08f, -0.47f, 0f, -0.59f, 0.22f)
                lineTo(2.74f, 8.87f)
                curveToRelative(-0.12f, 0.21f, -0.08f, 0.47f, 0.12f, 0.61f)
                lineToRelative(2.03f, 1.58f)
                curveToRelative(-0.05f, 0.3f, -0.09f, 0.63f, -0.09f, 0.94f)
                reflectiveCurveToRelative(0.02f, 0.64f, 0.07f, 0.94f)
                lineToRelative(-2.03f, 1.58f)
                curveToRelative(-0.18f, 0.14f, -0.23f, 0.41f, -0.12f, 0.61f)
                lineToRelative(1.92f, 3.32f)
                curveToRelative(0.12f, 0.22f, 0.37f, 0.29f, 0.59f, 0.22f)
                lineToRelative(2.39f, -0.96f)
                curveToRelative(0.5f, 0.38f, 1.03f, 0.7f, 1.62f, 0.94f)
                lineToRelative(0.36f, 2.54f)
                curveToRelative(0.05f, 0.24f, 0.24f, 0.41f, 0.48f, 0.41f)
                horizontalLineToRelative(3.84f)
                curveToRelative(0.24f, 0f, 0.44f, -0.17f, 0.47f, -0.41f)
                lineToRelative(0.36f, -2.54f)
                curveToRelative(0.59f, -0.24f, 1.13f, -0.56f, 1.62f, -0.94f)
                lineToRelative(2.39f, 0.96f)
                curveToRelative(0.22f, 0.08f, 0.47f, 0f, 0.59f, -0.22f)
                lineToRelative(1.92f, -3.32f)
                curveToRelative(0.12f, -0.22f, 0.07f, -0.47f, -0.12f, -0.61f)
                lineToRelative(-2.01f, -1.58f)
                close()
                moveTo(12f, 15.6f)
                curveToRelative(-1.98f, 0f, -3.6f, -1.62f, -3.6f, -3.6f)
                reflectiveCurveToRelative(1.62f, -3.6f, 3.6f, -3.6f)
                reflectiveCurveToRelative(3.6f, 1.62f, 3.6f, 3.6f)
                reflectiveCurveToRelative(-1.62f, 3.6f, -3.6f, 3.6f)
                close()
            }
        }
    }

    val Swap: ImageVector by lazy {
        buildIcon("Swap") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(6.99f, 11f)
                lineTo(3f, 15f)
                lineToRelative(3.99f, 4f)
                verticalLineToRelative(-3f)
                horizontalLineTo(14f)
                verticalLineToRelative(-2f)
                horizontalLineTo(6.99f)
                verticalLineToRelative(-3f)
                close()
                moveTo(21f, 9f)
                lineToRelative(-3.99f, -4f)
                verticalLineToRelative(3f)
                horizontalLineTo(10f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(7.01f)
                verticalLineToRelative(3f)
                lineTo(21f, 9f)
                close()
            }
        }
    }

    val Check: ImageVector by lazy {
        buildIcon("Check") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(9f, 16.17f)
                lineTo(4.83f, 12f)
                lineToRelative(-1.42f, 1.41f)
                lineTo(9f, 19f)
                lineTo(21f, 7f)
                lineToRelative(-1.41f, -1.41f)
                close()
            }
        }
    }

    val Warning: ImageVector by lazy {
        buildIcon("Warning") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(1f, 21f)
                horizontalLineToRelative(22f)
                lineTo(12f, 2f)
                lineTo(1f, 21f)
                close()
                moveTo(13f, 18f)
                horizontalLineToRelative(-2f)
                verticalLineToRelative(-2f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(2f)
                close()
                moveTo(13f, 14f)
                horizontalLineToRelative(-2f)
                verticalLineToRelative(-4f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(4f)
                close()
            }
        }
    }

    val Refresh: ImageVector by lazy {
        buildIcon("Refresh") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(17.65f, 6.35f)
                curveTo(16.2f, 4.9f, 14.21f, 4f, 12f, 4f)
                curveToRelative(-4.42f, 0f, -7.99f, 3.58f, -7.99f, 8f)
                reflectiveCurveToRelative(3.57f, 8f, 7.99f, 8f)
                curveToRelative(3.73f, 0f, 6.84f, -2.55f, 7.73f, -6f)
                horizontalLineToRelative(-2.08f)
                curveToRelative(-0.82f, 2.33f, -3.04f, 4f, -5.65f, 4f)
                curveToRelative(-3.31f, 0f, -6f, -2.69f, -6f, -6f)
                reflectiveCurveToRelative(2.69f, -6f, 6f, -6f)
                curveToRelative(1.66f, 0f, 3.14f, 0.69f, 4.22f, 1.78f)
                lineTo(13f, 11f)
                horizontalLineToRelative(7f)
                verticalLineTo(4f)
                lineToRelative(-2.35f, 2.35f)
                close()
            }
        }
    }

    val Share: ImageVector by lazy {
        buildIcon("Share") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(18f, 16.08f)
                curveToRelative(-0.76f, 0f, -1.44f, 0.3f, -1.96f, 0.77f)
                lineTo(8.91f, 12.7f)
                curveToRelative(0.05f, -0.23f, 0.09f, -0.46f, 0.09f, -0.7f)
                reflectiveCurveToRelative(-0.04f, -0.47f, -0.09f, -0.7f)
                lineToRelative(7.05f, -4.11f)
                curveToRelative(0.54f, 0.5f, 1.25f, 0.81f, 2.04f, 0.81f)
                curveToRelative(1.66f, 0f, 3f, -1.34f, 3f, -3f)
                reflectiveCurveToRelative(-1.34f, -3f, -3f, -3f)
                reflectiveCurveToRelative(-3f, 1.34f, -3f, 3f)
                curveToRelative(0f, 0.24f, 0.04f, 0.47f, 0.09f, 0.7f)
                lineTo(8.04f, 9.81f)
                curveTo(7.5f, 9.31f, 6.79f, 9f, 6f, 9f)
                curveToRelative(-1.66f, 0f, -3f, 1.34f, -3f, 3f)
                reflectiveCurveToRelative(1.34f, 3f, 3f, 3f)
                curveToRelative(0.79f, 0f, 1.5f, -0.31f, 2.04f, -0.81f)
                lineToRelative(7.12f, 4.16f)
                curveToRelative(-0.05f, 0.21f, -0.08f, 0.43f, -0.08f, 0.65f)
                curveToRelative(0f, 1.61f, 1.31f, 2.92f, 2.92f, 2.92f)
                reflectiveCurveToRelative(2.92f, -1.31f, 2.92f, -2.92f)
                curveToRelative(0f, -1.61f, -1.31f, -2.92f, -2.92f, -2.92f)
                close()
            }
        }
    }

    val Star: ImageVector by lazy {
        buildIcon("Star") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12f, 17.27f)
                lineTo(18.18f, 21f)
                lineToRelative(-1.64f, -7.03f)
                lineTo(22f, 9.24f)
                lineToRelative(-7.19f, -0.61f)
                lineTo(12f, 2f)
                lineTo(9.19f, 8.63f)
                lineTo(2f, 9.24f)
                lineToRelative(5.46f, 4.73f)
                lineTo(5.82f, 21f)
                close()
            }
        }
    }

    val Info: ImageVector by lazy {
        buildIcon("Info") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12f, 2f)
                curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
                reflectiveCurveToRelative(4.48f, 10f, 10f, 10f)
                reflectiveCurveToRelative(10f, -4.48f, 10f, -10f)
                reflectiveCurveTo(17.52f, 2f, 12f, 2f)
                close()
                moveTo(13f, 17f)
                horizontalLineToRelative(-2f)
                verticalLineToRelative(-6f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(6f)
                close()
                moveTo(13f, 9f)
                horizontalLineToRelative(-2f)
                verticalLineTo(7f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(2f)
                close()
            }
        }
    }

    // Custom vector: Dashboard / Grid
    val Dashboard: ImageVector by lazy {
        buildIcon("Dashboard") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(3f, 13f)
                horizontalLineToRelative(8f)
                verticalLineTo(3f)
                horizontalLineTo(3f)
                verticalLineToRelative(10f)
                close()
                moveTo(3f, 21f)
                horizontalLineToRelative(8f)
                verticalLineToRelative(-6f)
                horizontalLineTo(3f)
                verticalLineToRelative(6f)
                close()
                moveTo(13f, 21f)
                horizontalLineToRelative(8f)
                verticalLineToRelative(-10f)
                horizontalLineToRelative(-8f)
                verticalLineToRelative(10f)
                close()
                moveTo(13f, 3f)
                verticalLineToRelative(6f)
                horizontalLineToRelative(8f)
                verticalLineTo(3f)
                horizontalLineToRelative(-8f)
                close()
            }
        }
    }

    // Custom vector: Inventory / Box
    val Inventory: ImageVector by lazy {
        buildIcon("Inventory") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(20f, 2f)
                horizontalLineTo(4f)
                curveTo(2.9f, 2f, 2f, 2.9f, 2f, 4f)
                verticalLineToRelative(3.01f)
                curveTo(2f, 7.73f, 2.43f, 8.35f, 3f, 8.7f)
                verticalLineTo(20f)
                curveTo(3f, 21.1f, 3.9f, 22f, 5f, 22f)
                horizontalLineToRelative(14f)
                curveTo(20.1f, 22f, 21f, 21.1f, 21f, 20f)
                verticalLineTo(8.7f)
                curveTo(21.57f, 8.35f, 22f, 7.73f, 22f, 7.01f)
                verticalLineTo(4f)
                curveTo(22f, 2.9f, 21.1f, 2f, 20f, 2f)
                close()
                moveTo(15f, 14f)
                horizontalLineTo(9f)
                verticalLineToRelative(-2f)
                horizontalLineToRelative(6f)
                verticalLineToRelative(2f)
                close()
                moveTo(20f, 6f)
                horizontalLineTo(4f)
                verticalLineTo(4f)
                horizontalLineToRelative(16f)
                verticalLineToRelative(2f)
                close()
            }
        }
    }

    // Custom vector: QR Code
    val QrCode: ImageVector by lazy {
        buildIcon("QrCode") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(3f, 3f)
                horizontalLineToRelative(8f)
                verticalLineToRelative(8f)
                horizontalLineTo(3f)
                verticalLineTo(3f)
                close()
                moveTo(5f, 5f)
                verticalLineToRelative(4f)
                horizontalLineToRelative(4f)
                verticalLineTo(5f)
                horizontalLineTo(5f)
                close()
                moveTo(13f, 3f)
                horizontalLineToRelative(8f)
                verticalLineToRelative(8f)
                horizontalLineToRelative(-8f)
                verticalLineTo(3f)
                close()
                moveTo(15f, 5f)
                verticalLineToRelative(4f)
                horizontalLineToRelative(4f)
                verticalLineTo(5f)
                horizontalLineToRelative(-4f)
                close()
                moveTo(3f, 13f)
                horizontalLineToRelative(8f)
                verticalLineToRelative(8f)
                horizontalLineTo(3f)
                verticalLineToRelative(-8f)
                close()
                moveTo(5f, 15f)
                verticalLineToRelative(4f)
                horizontalLineToRelative(4f)
                verticalLineToRelative(-4f)
                horizontalLineTo(5f)
                close()
                moveTo(13f, 13f)
                horizontalLineToRelative(3f)
                verticalLineToRelative(3f)
                horizontalLineToRelative(-3f)
                close()
                moveTo(16f, 16f)
                horizontalLineToRelative(3f)
                verticalLineToRelative(3f)
                horizontalLineToRelative(-3f)
                close()
                moveTo(18f, 13f)
                horizontalLineToRelative(3f)
                verticalLineToRelative(3f)
                horizontalLineToRelative(-3f)
                close()
                moveTo(13f, 18f)
                horizontalLineToRelative(3f)
                verticalLineToRelative(3f)
                horizontalLineToRelative(-3f)
                close()
            }
        }
    }

    // Custom vector: Scanner / Viewfinder
    val Scanner: ImageVector by lazy {
        buildIcon("Scanner") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(9.5f, 6.5f)
                verticalLineToRelative(3f)
                horizontalLineToRelative(-3f)
                verticalLineToRelative(-3f)
                horizontalLineToRelative(3f)
                moveTo(11f, 5f)
                horizontalLineTo(5f)
                verticalLineToRelative(6f)
                horizontalLineToRelative(6f)
                verticalLineTo(5f)
                close()
                moveTo(9.5f, 14.5f)
                verticalLineToRelative(3f)
                horizontalLineToRelative(-3f)
                verticalLineToRelative(-3f)
                horizontalLineToRelative(3f)
                moveTo(11f, 13f)
                horizontalLineTo(5f)
                verticalLineToRelative(6f)
                horizontalLineToRelative(6f)
                verticalLineToRelative(-6f)
                close()
                moveTo(17.5f, 6.5f)
                verticalLineToRelative(3f)
                horizontalLineToRelative(-3f)
                verticalLineToRelative(-3f)
                horizontalLineToRelative(3f)
                moveTo(19f, 5f)
                horizontalLineToRelative(-6f)
                verticalLineToRelative(6f)
                horizontalLineToRelative(6f)
                verticalLineTo(5f)
                close()
                moveTo(13f, 13f)
                horizontalLineToRelative(1.5f)
                verticalLineToRelative(1.5f)
                horizontalLineTo(13f)
                close()
                moveTo(14.5f, 14.5f)
                horizontalLineTo(16f)
                verticalLineTo(16f)
                horizontalLineToRelative(-1.5f)
                close()
                moveTo(16f, 13f)
                horizontalLineToRelative(1.5f)
                verticalLineToRelative(1.5f)
                horizontalLineTo(16f)
                close()
                moveTo(13f, 16f)
                horizontalLineToRelative(1.5f)
                verticalLineToRelative(1.5f)
                horizontalLineTo(13f)
                close()
                moveTo(14.5f, 17.5f)
                horizontalLineTo(16f)
                verticalLineTo(19f)
                horizontalLineToRelative(-1.5f)
                close()
                moveTo(16f, 16f)
                horizontalLineToRelative(1.5f)
                verticalLineToRelative(1.5f)
                horizontalLineTo(16f)
                close()
                moveTo(17.5f, 14.5f)
                horizontalLineTo(19f)
                verticalLineTo(16f)
                horizontalLineToRelative(-1.5f)
                close()
                moveTo(17.5f, 17.5f)
                horizontalLineTo(19f)
                verticalLineTo(19f)
                horizontalLineToRelative(-1.5f)
                close()
                moveTo(22f, 7f)
                verticalLineTo(4f)
                curveTo(22f, 2.9f, 21.1f, 2f, 20f, 2f)
                horizontalLineToRelative(-3f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(3f)
                verticalLineToRelative(3f)
                horizontalLineToRelative(2f)
                close()
                moveTo(20f, 20f)
                horizontalLineToRelative(-3f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(3f)
                curveTo(21.1f, 22f, 22f, 21.1f, 22f, 20f)
                verticalLineToRelative(-3f)
                horizontalLineToRelative(-2f)
                verticalLineToRelative(3f)
                close()
                moveTo(4f, 7f)
                verticalLineTo(4f)
                horizontalLineToRelative(3f)
                verticalLineTo(2f)
                horizontalLineTo(4f)
                curveTo(2.9f, 2f, 2f, 2.9f, 2f, 4f)
                verticalLineToRelative(3f)
                horizontalLineToRelative(2f)
                close()
                moveTo(2f, 17f)
                verticalLineToRelative(3f)
                curveTo(2f, 21.1f, 2.9f, 22f, 4f, 22f)
                horizontalLineToRelative(3f)
                verticalLineToRelative(-2f)
                horizontalLineTo(4f)
                verticalLineToRelative(-3f)
                horizontalLineTo(2f)
                close()
            }
        }
    }

    // Custom vector: Wardrobe / Hanger
    val Wardrobe: ImageVector by lazy {
        buildIcon("Wardrobe") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12f, 4f)
                curveTo(10.9f, 4f, 10f, 4.9f, 10f, 6f)
                curveTo(10f, 7.1f, 10.9f, 8f, 12f, 8f)
                curveTo(12.7f, 8f, 13.3f, 7.6f, 13.7f, 7f)
                horizontalLineTo(15f)
                curveTo(14.5f, 8.4f, 13.4f, 9.4f, 12f, 9.8f)
                verticalLineTo(10f)
                lineTo(2.5f, 15.5f)
                curveTo(2.2f, 15.7f, 2f, 16.1f, 2f, 16.5f)
                curveTo(2f, 17.3f, 2.7f, 18f, 3.5f, 18f)
                horizontalLineTo(20.5f)
                curveTo(21.3f, 18f, 22f, 17.3f, 22f, 16.5f)
                curveTo(22f, 16.1f, 21.8f, 15.7f, 21.5f, 15.5f)
                lineTo(12f, 10f)
                verticalLineTo(6f)
                curveTo(12f, 6f, 12f, 5.5f, 11.5f, 5.5f)
                curveTo(11.2f, 5.5f, 11f, 5.7f, 11f, 6f)
                curveTo(11f, 6.6f, 11.4f, 7f, 12f, 7f)
                verticalLineTo(4f)
                close()
                moveTo(4.6f, 16f)
                lineTo(12f, 11.8f)
                lineTo(19.4f, 16f)
                horizontalLineTo(4.6f)
                close()
            }
        }
    }

    // Custom vector: Store / Boutique
    val Store: ImageVector by lazy {
        buildIcon("Store") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(20f, 4f)
                horizontalLineTo(4f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(16f)
                verticalLineTo(4f)
                close()
                moveTo(21f, 14f)
                verticalLineToRelative(-2f)
                lineToRelative(-1f, -5f)
                horizontalLineTo(4f)
                lineToRelative(-1f, 5f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(1f)
                verticalLineToRelative(6f)
                horizontalLineToRelative(10f)
                verticalLineToRelative(-6f)
                horizontalLineToRelative(4f)
                verticalLineToRelative(6f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(-6f)
                horizontalLineToRelative(1f)
                close()
                moveTo(12f, 18f)
                horizontalLineTo(6f)
                verticalLineToRelative(-4f)
                horizontalLineToRelative(6f)
                verticalLineToRelative(4f)
                close()
            }
        }
    }

    // Custom vector: Analytics / Trends
    val Analytics: ImageVector by lazy {
        buildIcon("Analytics") {
            path(fill = SolidColor(Color.Black)) {
                moveTo(3.5f, 18.5f)
                lineToRelative(6f, -6f)
                lineToRelative(4f, 4f)
                lineToRelative(7f, -7f)
                verticalLineToRelative(4f)
                horizontalLineToRelative(2f)
                verticalLineTo(5.5f)
                horizontalLineToRelative(-8f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(4.5f)
                lineToRelative(-5.5f, 5.5f)
                lineToRelative(-4f, -4f)
                lineToRelative(-7.4f, 7.4f)
                lineToRelative(1.4f, 1.4f)
                close()
            }
        }
    }
}

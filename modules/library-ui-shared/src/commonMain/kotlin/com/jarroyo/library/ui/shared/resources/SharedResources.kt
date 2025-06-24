package com.jarroyo.library.ui.shared.resources

import org.jetbrains.compose.resources.DrawableResource
import composeapp.modules.library_ui_shared.generated.resources.Res
import composeapp.modules.library_ui_shared.generated.resources.ui_ic_add
import composeapp.modules.library_ui_shared.generated.resources.ui_ic_delete
import composeapp.modules.library_ui_shared.generated.resources.ui_ic_user

object SharedResources {
    val ui_ic_add: DrawableResource
        get() = Res.drawable.ui_ic_add

    val ui_ic_delete: DrawableResource
        get() = Res.drawable.ui_ic_delete

    val ui_ic_user: DrawableResource
        get() = Res.drawable.ui_ic_user
}

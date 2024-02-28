//
//  iosAppApp.swift
//  iosApp
//
//  Created by Javier Arroyo on 25/2/24.
//

import SwiftUI
import ComposeApp

@main
struct iosAppApp: App {
    init() {
        KoinAppModuleKt.doInitKoin(appDeclaration: {_ in })
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

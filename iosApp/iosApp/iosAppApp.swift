//
//  iosAppApp.swift
//  iosApp
//
//  Created by Javier Arroyo on 25/2/24.
//

import ComposeApp
import FirebaseCore
import SwiftUI

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_: UIApplication,
                     didFinishLaunchingWithOptions _: [UIApplication.LaunchOptionsKey: Any]? = nil) -> Bool
    {
        FirebaseApp.configure()

        return true
    }
}

@main
struct iosAppApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    init() {
        KoinAppModuleKt.doInitKoin(additionalModules: [])
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

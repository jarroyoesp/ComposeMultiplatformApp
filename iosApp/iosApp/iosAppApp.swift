//
//  iosAppApp.swift
//  iosApp
//
//  Created by Javier Arroyo on 25/2/24.
//

import SwiftUI
import ComposeApp
import FirebaseCore

class AppDelegate: NSObject, UIApplicationDelegate {
  func application(_ application: UIApplication,
                   didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
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

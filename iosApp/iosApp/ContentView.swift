//
//  ContentView.swift
//  iosApp
//
//  Created by Javier Arroyo on 25/2/24.
//

import ComposeApp
import SwiftUI

struct ContentView: UIViewControllerRepresentable {
    func makeUIViewController(context _: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_: UIViewController, context _: Context) {}
}

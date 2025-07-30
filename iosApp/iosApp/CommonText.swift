//
// Created by giuseppe.buzzanca on 29/07/25.
//

import Foundation
import SwiftUI
import Feature

struct CommonText: UIViewControllerRepresentable {
    let title: String
     @Binding var measuredSize: CGSize

    func makeUIViewController(
        context: Context
    ) -> UIViewController {
        let scale = UIScreen.main.scale
        return CommonTextKt.CommonTextViewController(
                label: title
            ) {
                width,
                height in
                // Convert from pixels to points and update SwiftUI state
                DispatchQueue.main.async {
                    measuredSize = CGSize(
                        width: CGFloat(
                            truncating: width
                        ) / scale,
                        height: CGFloat(
                            truncating: height
                        ) / scale
                    )
                }
            }
    }
    
    func updateUIViewController(
        _ uiViewController: UIViewController,
        context: Context
    ) {
    }
}


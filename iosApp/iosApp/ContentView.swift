import SwiftUI
import Feature

struct ContentView: View {
    @ObservedObject private(set) var viewModel: ViewModel
    //@State private var composeSize = CGSize(width: 100, height: 100) // Initial size

    var body: some View {
        HStack {
            switch viewModel.result {
            case let error as CommonResultError:
                Text(error.exception.message ?? "Unknown error")
            case let success as CommonResultSuccess<NSString>:
                Text(success.data! as String)
            default:
                ProgressView()
            }
        }
        .task {
            await viewModel.startObserving()
        }
    }
}

extension ContentView {
    @MainActor
    class ViewModel: ObservableObject {
        @Published var result: CommonResult = CommonResultLoading()

        func startObserving() async {
            for await value in TemplateViewModelKt.get().result {
                self.result = value
            }
        }
    }
}

# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a **Kotlin Multiplatform (KMP)** project targeting Android and iOS platforms. It follows **Clean Architecture** principles with strict unidirectional data flow: `UI ← ViewModel ← UseCase ← Repository ← DataSource`. The project uses **Jetpack Compose** for Android UI and **SwiftUI** for iOS UI.

## Architecture & Structure

### Module Organization
- **Feature-First Approach**: Components belong in feature modules (e.g., `:feature:template`) by default
- **Core Modules**: Only promote to core modules (`:core:data`, `:core:domain`, `:core:model`) when reused by multiple features
- **Modular Structure**:
  - `/composeApp` - Shared Compose Multiplatform application code
  - `/core/*` - Shared core modules (common, data, database, network, model, etc.)
  - `/feature/*` - Feature-specific modules following Clean Architecture
  - `/iosApp` - iOS application entry point and SwiftUI code

### Key Technologies
- **Dependency Injection**: Koin with `factory` for most objects, `viewModel` for ViewModels
- **Database**: SQLDelight as Single Source of Truth (offline-first)
- **Networking**: Ktor client with kotlinx.serialization
- **Testing**: Kotlin Test, Koin Test utilities, Mokkery for mocking
- **UI**: Jetpack Compose (Android) + SwiftUI (iOS)

### Data Model Strategy
- **Network Models** (`data.model`): Raw JSON with `@Serializable` annotations
- **Domain Models** (`domain.model`): Clean business entities without framework annotations  
- **UI Models** (`model`): View-specific state mapped by ViewModels

## Common Development Tasks

### Building
```bash
./gradlew build                    # Build all targets
./gradlew assemble                 # Assemble without tests
./gradlew clean                    # Clean build directories
```

### Testing
```bash
./gradlew allTests                 # Run all tests with aggregated report
./gradlew test                     # Run unit tests for all variants
./gradlew testDebugUnitTest        # Run Android unit tests (debug)
./gradlew iosSimulatorArm64Test    # Run iOS simulator tests (ARM64)
./gradlew iosX64Test               # Run iOS simulator tests (x64)
./gradlew check                    # Run all verification tasks
```

### iOS Development
```bash
./gradlew linkDebugFrameworkIosArm64        # Build iOS framework (device)
./gradlew linkDebugFrameworkIosSimulatorArm64  # Build iOS framework (simulator)
./gradlew embedAndSignAppleFrameworkForXcode   # Xcode integration task
```

### Code Quality
```bash
./gradlew lint                     # Run Android lint
./gradlew lintFix                  # Fix lint issues automatically  
./gradlew checkKotlinGradlePluginConfigurationErrors  # Check KMP config
```

## Development Guidelines

### Code Conventions
- **File Granularity**: One primary declaration (class, Composable, View) per file
- **No Comments**: Do not add code comments unless explicitly requested
- **UI Previews**: Always include `@Preview` (Compose) and `#Preview` (SwiftUI)
- **Testing**: Use `BaseKoinTest` from `:core:testing` for integration tests

### Layer-Specific Rules
- **ViewModels**: Only mandatory layer beyond UI; can interact directly with data sources for simple features
- **Use Cases**: Introduce only for non-trivial business logic
- **Repositories**: Use SQLDelight as SoT in offline-first approach
- **Network**: Parse JSON directly into Network Models using kotlinx.serialization

### SwiftUI-KMP Integration Pattern
1. Create `ObservableObject` wrapper for KMP ViewModel
2. Inject KMP ViewModel using Koin
3. Collect ViewModel's `stateFlow` and update `@Published` property
4. Use `@StateObject` and `.task` modifier in SwiftUI Views

### Dependency Injection
- **Reuse Core Modules**: Prefer reusing DI from `:core:network`, `:core:database`
- **Scope Correctly**: Use `factory` for most objects, `viewModel` for ViewModels
- **Testing**: Use Koin's `MockEngine` for Ktor to return fake JSON responses
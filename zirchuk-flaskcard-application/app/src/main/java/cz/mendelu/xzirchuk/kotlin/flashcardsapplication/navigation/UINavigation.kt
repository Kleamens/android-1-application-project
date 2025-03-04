package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.navigation

sealed class UINavigation(var route:String) {
    object AddCardtoDeck : UINavigation(route = "add-card-to-deck")
    object TestFlashcards : UINavigation(route = "test-flashcards")

    object TestFillGap : UINavigation(route = "test-fill-gap")

    object EditDeck:UINavigation(route = "edit-deck")
    object TestChooseTest : UINavigation(route = "test-test-format")
    object SplashScreen : UINavigation(route = "splash-screen")
}

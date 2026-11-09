package dk.sundhedsdatastyrelsen.epportal.utils

/** For exceptions with messages that should be shown to the user */
class PublicException(message: String, parent: Throwable? = null) : Exception(message, parent) {
}

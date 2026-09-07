package dk.sundhedsdatastyrelsen.epportal.app.filters

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.startsWith
import jakarta.servlet.FilterChain
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UserTrackingFilterTest {

    private lateinit var filter: UserTrackingFilter
    private lateinit var chain: FilterChain
    private lateinit var request: HttpServletRequest
    private lateinit var response: HttpServletResponse

    @BeforeTest
    fun setup() {
        filter = UserTrackingFilter()
        chain = mock()
        request = mock()
        response = mock()
        whenever(request.requestURI).thenReturn("/some/path")
        // Default: no cookies
        whenever(request.cookies).thenReturn(null)
    }

    @Test
    fun `doFilter sets tracking cookie when missing`() {
        filter.doFilter(request, response, chain)

        val cookieCaptor = argumentCaptor<String>()
        verify(response).addHeader(eq("Set-Cookie"), cookieCaptor.capture())
        val cookie = cookieCaptor.firstValue
        assertThat(cookie).startsWith("sds-epportal-id=")
        assertThat(cookie).contains("HttpOnly; SameSite=None; Secure; Max-Age: 86400; Path=/")

        verify(chain).doFilter(any(), eq(response))
    }

    @Test
    fun `doFilter skips tracking for saml metadata`() {
        whenever(request.requestURI).thenReturn("/saml/metadata")

        filter.doFilter(request, response, chain)

        // No cookie added
        verify(chain).doFilter(any(), eq(response))
    }

    @Test
    fun `clearUserTrackingCookie sets deletion cookie`() {
        UserTrackingFilter.clearUserTrackingCookie(response)

        val cookieCaptor = argumentCaptor<Cookie>()
        verify(response).addCookie(cookieCaptor.capture())
        val cookie = cookieCaptor.firstValue
        assertEquals("sds-epportal-id", cookie.name)
        assertEquals("", cookie.value)
        assertEquals(0, cookie.maxAge)
        assertEquals("/", cookie.path)
    }
}

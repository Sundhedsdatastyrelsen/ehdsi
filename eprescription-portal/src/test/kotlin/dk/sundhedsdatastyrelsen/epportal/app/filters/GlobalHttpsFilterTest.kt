package dk.sundhedsdatastyrelsen.epportal.app.filters

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
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
import kotlin.test.assertTrue

class GlobalHttpsFilterTest {

    private lateinit var filter: GlobalHttpsFilter
    private lateinit var mockChain: FilterChain
    private lateinit var mockRequest: HttpServletRequest
    private lateinit var mockResponse: HttpServletResponse

    @BeforeTest
    fun setUp() {
        filter = GlobalHttpsFilter()
        mockChain = mock()
        mockRequest = mock()
        mockResponse = mock()
    }

    @Test
    fun `wrap HttpServletRequest with HttpsRequestWrapper`() {
        whenever(mockRequest.requestURI).thenReturn("/test")

        filter.doFilter(mockRequest, mockResponse, mockChain)

        val captor = argumentCaptor<ServletRequest>()
        verify(mockChain).doFilter(captor.capture(), eq(mockResponse))

        val capturedRequest = captor.firstValue
        assertTrue(capturedRequest is HttpsRequestWrapper)

        val wrapper = capturedRequest as HttpsRequestWrapper
        assertEquals("https", wrapper.scheme)
        assertEquals("HTTPS/1.1", wrapper.protocol)
        assertTrue(wrapper.isSecure)
        assertEquals(443, wrapper.serverPort)
    }

    @Test
    fun `maintain original request properties`() {
        whenever(mockRequest.requestURI).thenReturn("/test/path")
        whenever(mockRequest.queryString).thenReturn("param=value")
        whenever(mockRequest.method).thenReturn("POST")
        whenever(mockRequest.contentType).thenReturn("application/json")
        whenever(mockRequest.getHeader("User-Agent")).thenReturn("TestAgent")

        filter.doFilter(mockRequest, mockResponse, mockChain)

        val captor = argumentCaptor<ServletRequest>()
        verify(mockChain).doFilter(captor.capture(), eq(mockResponse))

        val wrapper = captor.firstValue as HttpsRequestWrapper
        assertEquals("/test/path", wrapper.requestURI)
        assertEquals("param=value", wrapper.queryString)
        assertEquals("POST", wrapper.method)
        assertEquals("application/json", wrapper.contentType)
        assertEquals("TestAgent", wrapper.getHeader("User-Agent"))
    }

    @Test
    fun `override security-related properties`() {
        whenever(mockRequest.scheme).thenReturn("http")
        whenever(mockRequest.protocol).thenReturn("HTTP/1.1")
        whenever(mockRequest.isSecure).thenReturn(false)
        whenever(mockRequest.serverPort).thenReturn(8080)
        whenever(mockRequest.getHeader("X-Forwarded-Proto")).thenReturn("http")
        whenever(mockRequest.getHeader("X-Forwarded-Port")).thenReturn("8080")

        filter.doFilter(mockRequest, mockResponse, mockChain)

        val captor = argumentCaptor<ServletRequest>()
        verify(mockChain).doFilter(captor.capture(), eq(mockResponse))

        val wrapper = captor.firstValue as HttpsRequestWrapper
        assertEquals("https", wrapper.scheme)
        assertEquals("HTTPS/1.1", wrapper.protocol)
        assertTrue(wrapper.isSecure)
        assertEquals(443, wrapper.serverPort)
        assertEquals("https", wrapper.getHeader("X-Forwarded-Proto"))
        assertEquals("443", wrapper.getHeader("X-Forwarded-Port"))
    }

    @Test
    fun `preserve response object`() {
        val customResponse = mock<HttpServletResponse>()
        filter.doFilter(mockRequest, customResponse, mockChain)
        verify(mockChain).doFilter(any(), eq(customResponse))
    }

}

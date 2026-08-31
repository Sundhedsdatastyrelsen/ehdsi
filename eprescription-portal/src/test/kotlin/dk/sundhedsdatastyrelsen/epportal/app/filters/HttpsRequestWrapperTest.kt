package dk.sundhedsdatastyrelsen.epportal.app.filters

import jakarta.servlet.http.HttpServletRequest
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.BeforeTest
import kotlin.test.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class HttpsRequestWrapperTest {

    private lateinit var mockRequest: HttpServletRequest
    private lateinit var wrapper: HttpsRequestWrapper

    @BeforeTest
    fun setUp() {
        mockRequest = mock()
        wrapper = HttpsRequestWrapper(mockRequest)
    }

    @Test
    fun getScheme() {
        whenever(mockRequest.scheme).thenReturn("http")
        val result = wrapper.scheme
        assertEquals("https", result)
    }

    @Test
    fun getProtocol() {
        whenever(mockRequest.protocol).thenReturn("HTTP/1.1")
        val result = wrapper.protocol
        assertEquals("HTTPS/1.1", result)
    }

    @Test
    fun isSecure() {
        whenever(mockRequest.isSecure).thenReturn(false)
        val result = wrapper.isSecure
        assertTrue(result)
    }

    @Test
    fun getServerPort() {
        whenever(mockRequest.serverPort).thenReturn(8080)
        val result = wrapper.serverPort
        assertEquals(443, result)
    }

    @Test
    fun `getRequestURL - httpurl`() {
        val originalUrl = StringBuffer("http://localhost:8080/test")
        whenever(mockRequest.requestURL).thenReturn(originalUrl)
        val result = wrapper.requestURL
        assertEquals("https://localhost:8080/test", result.toString())
    }

    @Test
    fun `getRequestURL - existing url`() {
        val originalUrl = StringBuffer("https://localhost:443/test")
        whenever(mockRequest.requestURL).thenReturn(originalUrl)
        val result = wrapper.requestURL
        assertEquals("https://localhost:443/test", result.toString())
    }

    @Test
    fun `getHeader - X-Forwarded-Proto`() {
        whenever(mockRequest.getHeader("X-Forwarded-Proto")).thenReturn("http")
        val result = wrapper.getHeader("X-Forwarded-Proto")
        assertEquals("https", result)
    }

    @Test
    fun `getHeader - X-Forwarded-Port`() {
        whenever(mockRequest.getHeader("X-Forwarded-Port")).thenReturn("8080")
        val result = wrapper.getHeader("X-Forwarded-Port")
        assertEquals("443", result)
    }

    @Test
    fun `getHeader - X-Forwarded-Host`() {
        whenever(mockRequest.getHeader("X-Forwarded-Host")).thenReturn("example.com")
        val result = wrapper.getHeader("X-Forwarded-Host")
        assertEquals("example.com", result)
    }


    @Test
    fun `getHeader should delegate to original request for other headers`() {
        whenever(mockRequest.getHeader("User-Agent")).thenReturn("TestAgent")
        val result = wrapper.getHeader("User-Agent")
        assertEquals("TestAgent", result)
        verify(mockRequest).getHeader("User-Agent") //Check that the mock was actually called
    }


    @Test
    fun `getRemoteAddr - X-Forwarded-For`() {
        whenever(mockRequest.getHeader("X-Forwarded-For")).thenReturn("192.168.1.100, 10.0.0.1")
        whenever(mockRequest.remoteAddr).thenReturn("127.0.0.1")
        val result = wrapper.remoteAddr
        assertEquals("192.168.1.100", result)
    }

    @Test
    fun `getRemoteAddr - original`() {
        whenever(mockRequest.getHeader("X-Forwarded-For")).thenReturn("")
        whenever(mockRequest.remoteAddr).thenReturn("127.0.0.1")
        val result = wrapper.remoteAddr
        assertEquals("127.0.0.1", result)
    }


    @Test
    fun `getRequestURI - delegate`() {
        whenever(mockRequest.requestURI).thenReturn("/test/path")
        val result = wrapper.requestURI
        assertEquals("/test/path", result)
        verify(mockRequest).requestURI
    }

    @Test
    fun `getQueryString - delegate`() {
        whenever(mockRequest.queryString).thenReturn("param1=value1&param2=value2")
        val result = wrapper.queryString
        assertEquals("param1=value1&param2=value2", result)
        verify(mockRequest).queryString
    }

    @Test
    fun `wrapper - other request methods`() {
        val testMap = HashMap<String, Array<String>>()
        testMap["key1"] = arrayOf("value1")
        testMap["key2"] = arrayOf("value2")
        whenever(mockRequest.parameterMap).thenReturn(testMap)
        whenever(mockRequest.method).thenReturn("POST")
        whenever(mockRequest.contentType).thenReturn("application/json")
        val paramMap = wrapper.parameterMap
        val method = wrapper.method
        val contentType = wrapper.contentType
        assertEquals(testMap, paramMap)
        assertEquals("POST", method)
        assertEquals("application/json", contentType)
    }
}

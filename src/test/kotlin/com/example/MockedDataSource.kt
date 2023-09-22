package com.example

import com.example.services.DataSource
import com.example.services.RealDataSource
import io.micronaut.context.annotation.Replaces
import jakarta.inject.Singleton
import org.mockito.Mockito
import java.sql.Connection

/**
 * 当你使用@Replaces(RealDataSource::class)注解，你告诉Micronaut在测试环境中用MockedDataSource替换RealDataSource。这是Micronaut的一种强大特性，它允许你为不同的运行环境（例如测试环境、开发环境或生产环境）提供不同的bean实现。
 *
 * 在你的测试中：
 *
 * MockedDataSource替换了应用程序中默认使用的数据源。
 * 当SignupService或任何其他组件尝试从数据源获取数据库连接时，它实际上会从MockedDataSource中获得一个模拟的连接。
 * 由于mockedConnection是一个模拟对象，任何对这个连接的操作（如执行SQL查询或更新）都不会真的执行，除非你明确地使用Mockito定义了某个操作的行为。
 * 因此，不会有任何实际的数据库操作，从而确保你的测试不会与真实的数据库互动。
 * 这种方法允许你完全控制数据库连接的行为，确保测试是可重复的、隔离的，并且不会受到外部数据库状态的影响。
 */
@Singleton
@Replaces(RealDataSource::class)
class MockedDataSource : DataSource {
    var mockedConnection: Connection = Mockito.mock(Connection::class.java)

    override fun getConnection(): Connection {
        return mockedConnection
    }
}
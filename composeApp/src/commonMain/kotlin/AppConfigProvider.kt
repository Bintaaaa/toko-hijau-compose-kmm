import com.bijan.libraries.core.AppConfig

class AppConfigProvider : AppConfig {
    override val baseUrl: String
        get() = BuildKonfig.BASE_URL
}
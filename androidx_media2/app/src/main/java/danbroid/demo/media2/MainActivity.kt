package danbroid.demo.media2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import danbroid.demo.media2.content.rootContent
import danbroid.util.menu.navigation.createMenuGraph
import danbroid.util.menu.ui.MenuImplementation
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


  init {
    MenuImplementation.rootContent = {
      rootContent
    }
  }


  protected val navHostFragment: NavHostFragment
    get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

  override fun onCreate(savedInstanceState: Bundle?) {
    log.info("onCreate()")

    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)
    navHostFragment.navController.apply {
      createMenuGraph()
      setupActionBarWithNavController(this)
    }

    log.warn("intent $intent")
    log.warn("data:${intent?.data}")
    intent?.extras?.also {
      it.keySet()?.forEach {
        log.debug("KEY: $it ${intent?.extras?.get(it)}")
      }
    }
  }


  override fun onNewIntent(intent: Intent?) {
    log.warn("onNewIntent!() $intent")
    log.warn("data:${intent?.data}")
    log.warn("extras:${intent?.extras}")

    super.onNewIntent(intent)
  }

  override fun onSupportNavigateUp() = navHostFragment.navController.navigateUp() || super.onSupportNavigateUp()


  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }


  override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    return when (item.itemId) {
      R.id.action_settings -> true
      else -> super.onOptionsItemSelected(item)
    }
  }

}

private val log = org.slf4j.LoggerFactory.getLogger(MainActivity::class.java)



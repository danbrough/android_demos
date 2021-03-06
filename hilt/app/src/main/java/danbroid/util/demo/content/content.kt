package danbroid.util.demo.content

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial
import danbroid.util.demo.DemoNavGraph
import danbroid.util.demo.R
import danbroid.util.demo.TestModel
import danbroid.util.demo.URI_CONTENT_PREFIX
import danbroid.util.menu.Icons
import danbroid.util.menu.MenuItemBuilder
import danbroid.util.menu.menu
import danbroid.util.menu.rootMenu
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.slf4j.LoggerFactory

private object _content

private val log = LoggerFactory.getLogger(_content::class.java.`package`!!.name)

@ExperimentalCoroutinesApi
fun rootContent(context: Context) = context.rootMenu<MenuItemBuilder> {
  id = URI_CONTENT_PREFIX
  titleID = R.string.app_name



  menu {
    title = "Test1"
    onClick = {
      log.info("clicked Test1")
      val model: TestModel by fragment.viewModels()
      Toast.makeText(context, "Long click handled: count:${model.count}", Toast.LENGTH_SHORT).show()
      model.count++
      proceed()
    }

    menu {
      title = "Fragment Test"
      id = DemoNavGraph.deep_link.test
    }
  }


  menu {
    titleID = R.string.lbl_settings
    id = DemoNavGraph.deep_link.settings
    icon = Icons.iconicsIcon(GoogleMaterial.Icon.gmd_settings)
  }


}

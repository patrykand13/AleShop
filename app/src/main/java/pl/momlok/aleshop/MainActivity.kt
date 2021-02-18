package pl.momlok.aleshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fm = supportFragmentManager
        val categoriesFragment = CategoriesFragment()
        val cartFragment = CartFragment()

        menu_button.setOnItemSelectedListener { id ->
            when(id){
                R.id.categories_item -> fm.beginTransaction().replace(R.id.container_frag, categoriesFragment).commit()
                R.id.cart_item -> fm.beginTransaction().replace(R.id.container_frag, cartFragment).commit()
            }
        }
    }
}
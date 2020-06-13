package soup.animation.sample.utils

import android.os.Bundle
import androidx.annotation.AnimRes
import androidx.collection.arrayMapOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentNavigator(
    private val fragmentManager: FragmentManager,
    private val containerId: Int
) {

    private val fragmentSavedStates = arrayMapOf<String, Fragment.SavedState?>()
    private var lastScene: String? = null

    fun navigate(
        newTag: String,
        @AnimRes enter: Int,
        @AnimRes exit: Int,
        newInstance: () -> Fragment
    ): Boolean {
        val previousTag: String? = lastScene
        if (previousTag == newTag) {
            return false
        }
        lastScene = newTag

        val fragmentTransaction = fragmentManager.beginTransaction()
            .disallowAddToBackStack()
            .setReorderingAllowed(true)

        // TODO: Fragment Animations
        //if (previousTag != null) {
        //    fragmentTransaction.setCustomAnimations(enter, exit)
        //}

        if (previousTag != null) {
            val previous = saveFragment(previousTag)
            if (previous != null) {
                fragmentTransaction.hide(previous)
            }
        }

        val restore = restoreFragment(newTag)
        if (restore == null) {
            fragmentTransaction.add(containerId, newInstance(), newTag)
        } else {
            fragmentTransaction.show(restore)
        }
        fragmentTransaction.commitNow()
        return true
    }

    private fun restoreFragment(tag: String): Fragment? {
        val newFragment = fragmentManager.findFragmentByTag(tag)
        if (newFragment?.isAdded == false) {
            newFragment.setInitialSavedState(fragmentSavedStates[tag])
        }
        return newFragment
    }

    private fun saveFragment(tag: String): Fragment? {
        val previousFragment = fragmentManager.findFragmentByTag(tag)
        if (previousFragment?.isAdded == true) {
            fragmentSavedStates[tag] = fragmentManager.saveFragmentInstanceState(previousFragment)
        }
        return previousFragment
    }

    fun onSaveInstanceState(outState: Bundle) {
        lastScene.let {
            outState.putString(KEY_LAST_TAG, it)
        }
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        lastScene = savedInstanceState.getString(KEY_LAST_TAG)
    }

    companion object {

        private const val KEY_LAST_TAG = "FragmentNavigator:last_tag"
    }
}

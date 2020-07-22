package guide.graphql.toc.ui.chapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialSharedAxis
import guide.graphql.toc.R
import guide.graphql.toc.databinding.ChaptersFragmentBinding

class ChaptersFragment : Fragment() {
    private var _binding: ChaptersFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChaptersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val backward = MaterialSharedAxis(MaterialSharedAxis.X, false)
        reenterTransition = backward

        val forward = MaterialSharedAxis(MaterialSharedAxis.X, true)
        exitTransition = forward
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =
            ChaptersAdapter(
                requireContext()
            ) { chapter ->
                findNavController().navigate(
                    ChaptersFragmentDirections.viewSections(
                        chapterId = 10,
                        chapterNumber = 10,
                        chapterTitle = getString(
                            R.string.chapter_title,
                            "10",
                            "Android Dev"
                        )
                    )
                )
            }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.chapters.layoutManager = layoutManager

        val itemDivider = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.chapters.addItemDecoration(itemDivider)
        binding.chapters.adapter = adapter

        adapter.updateChapters(listOf("Android Dev"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showErrorMessage(errorMessage: String) {
        Toast.makeText(
            requireContext(),
            getString(R.string.graphql_error, errorMessage),
            Toast.LENGTH_SHORT
        ).show()
    }
}
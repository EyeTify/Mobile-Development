package com.bangkit.eyetify.ui.fragment

import android.annotation.SuppressLint
import android.app.AppOpsManager
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.eyetify.R
import com.bangkit.eyetify.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import android.os.Process
import android.provider.Settings
import androidx.core.content.ContextCompat
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (checkUsageStatePermission()) {
            showUsageStats()
        } else {
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }
    }

    private fun showUsageStats() {
        val usageStatsManager = requireContext().getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val cal = Calendar.getInstance()

        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)

        val startTime = cal.timeInMillis
        val endTime = System.currentTimeMillis()

        val queryUsageStats = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime, endTime
        )

        var totalTimeForeground = 0L
        queryUsageStats.forEach { usageStats ->
            totalTimeForeground += usageStats.totalTimeInForeground
        }

        // Kurangi 3 jam dari totalTimeForeground
        val totalTimeForegroundAdjusted = totalTimeForeground - 3 * 60 * 60 * 1000

        val formattedTime = formatTime(totalTimeForegroundAdjusted)
        binding.infoResultActivePhone.text = formattedTime

        val maxDurationPhone = 8 * 60 * 60 * 1000 // 12 jam dalam milidetik

        val percentage = (totalTimeForegroundAdjusted.toFloat() / maxDurationPhone.toFloat()) * 100

        binding.progressBar.apply {
            progressMax = 100f
            setProgressWithAnimation(percentage, 3000)
            progressBarWidth = 10f
            backgroundProgressBarWidth = 10f
            progressBarColor = when {
                percentage <= 60 -> Color.GREEN
                percentage <= 100 -> Color.rgb(255, 165, 0)
                else -> Color.RED
            }
            roundBorder = true
        }

        binding.infoResultStatusActivePhone.apply {
            when {
                percentage <= 60 -> {
                    text = context.getString(R.string.safe)
                    setTextColor(ContextCompat.getColor(context, R.color.succes))
                }
                percentage <= 100 -> {
                    text = context.getString(R.string.warning)
                    setTextColor(ContextCompat.getColor(context, R.color.warning))
                }
                else -> {
                    text = context.getString(R.string.danger)
                    setTextColor(ContextCompat.getColor(context, R.color.danger))
                }
            }
        }

        // Set left card description
        val maxDurationString = formatTime(8 * 60 * 60 * 1000) // 8 jam 30 menit dalam milidetik
        binding.smallCardLeftDescription.text = maxDurationString

        // Hitung waktu tersisa
        val remainingTime = maxDurationPhone - totalTimeForegroundAdjusted
        val remainingTimeFormatted = formatTime(remainingTime)
        binding.smallCardRightDescription.text = remainingTimeFormatted
    }

    @SuppressLint("DefaultLocale")
    private fun formatTime(timeInMillis: Long): String {
        val seconds = timeInMillis / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        return String.format("%02d ${getString(R.string.hours)}, %02d ${getString(R.string.minutes)}", hours, remainingMinutes)
    }

    private fun formatDate(timeInMillis: Long): String {
        val date = java.util.Date(timeInMillis)
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return format.format(date)
    }

    private fun checkUsageStatePermission(): Boolean {
        val appOpsManager = requireContext().getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOpsManager.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), requireContext().packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

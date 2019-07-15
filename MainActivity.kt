package com.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.activity_statistiques.textViewEndDate
import kotlinx.android.synthetic.main.activity_statistiques.textViewNext
import kotlinx.android.synthetic.main.activity_statistiques.textViewPrevious
import kotlinx.android.synthetic.main.activity_statistiques.textViewStartDate
import kotlinx.android.synthetic.main.activity_statistiques_2.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*


class MainActivity : AppCompatActivity(), OnChartValueSelectedListener {

    lateinit var chart : HorizontalBarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setSupportActionBar(toolbar)
        toolbarMenu.visibility = View.INVISIBLE
        toolbarMenu.setOnClickListener {
            val goMenu = Intent(this@MainActivity, ToolbarMenu::class.java)
            startActivity(goMenu)
        }

        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_ios_white_18dp)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        this.title = "Statistiques"

        // Gestion des clicks sur le bouton précédent et bouton suivant ("<" et ">")
        textViewPrevious.setOnClickListener {
            finish()
        }

        textViewNext.setOnClickListener {
            Toast.makeText(this@MainActivity, "Next", Toast.LENGTH_SHORT).show()
        }

        // Définir la date de début de stats par défaut au 1er janvier de l'année en cours
        val cal = Calendar.getInstance()
        cal.set(Calendar.MONTH, 0)
        cal.set(Calendar.DAY_OF_MONTH, 1)
        val year_start = cal.get(Calendar.YEAR)
        val month_start = cal.get(Calendar.MONTH) // Mois de 0 à 11
        val day_start = cal.get(Calendar.DAY_OF_MONTH)
        textViewStartDate.setText("${String.format("%02d", day_start)}/${String.format("%02d", month_start+1)}/${String.format("%04d", year_start)}")

        // Définir la date de fin de stats par défaut au dernier jour de l'année en cours
        cal.set(Calendar.MONTH, 11)
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
        val year_end = cal.get(Calendar.YEAR)
        val month_end = cal.get(Calendar.MONTH) // Mois de 0 à 11
        val day_end = cal.get(Calendar.DAY_OF_MONTH)
        textViewEndDate.setText("${String.format("%02d", day_end)}/${String.format("%02d", month_end+1)}/${String.format("%04d", year_end)}")

        chart = chart2
        chart.setDrawBarShadow(false)
        chart.setDrawValueAboveBar(true)
        chart.description.isEnabled = false
        chart.legend.isEnabled = false
        chart.setPinchZoom(false)
        chart.setDrawGridBackground(false)

        val xAxis = chart.xAxis
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.setDrawAxisLine(true)
        xAxis.setDrawGridLines(false)
        xAxis.isEnabled = true
        xAxis.labelCount = 12

        val yLeft = chart.axisLeft
        yLeft.axisMaximum = 150f
        yLeft.axisMinimum = 0f
        yLeft.isEnabled = false

        val  yRight = chart.axisRight
        yRight.setDrawAxisLine(true)
        yRight.setDrawGridLines(true)
        yRight.isEnabled = true

        chart.setFitBars(true)
        chart.animateY(2500)

        val labels = arrayOf("janvier", "février", "mars", "avril", "mai", "juin", "juillet", "aôut", "septembre", "octobre", "novembre", "décembre")

        val formatter = object : ValueFormatter() {

            /*override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                try {
                    return labels[value.toInt()]
                } catch(t: Throwable) {
                    return ""
                }
            }*/

            override fun getFormattedValue(value: Float): String {
                return labels[value.toInt()]
            }
        }
        xAxis.valueFormatter = formatter


        setData()

        chart.invalidate()

    }

    private fun setData() {

        val values = ArrayList<BarEntry>()
        values.add(BarEntry(0f, 80f))
        values.add(BarEntry(1f, 70f))
        values.add(BarEntry(2f, 60f))
        values.add(BarEntry(3f, 50f))
        values.add(BarEntry(4f, 40f))
        values.add(BarEntry(5f, 30f))
        values.add(BarEntry(6f, 20f))
        values.add(BarEntry(7f, 25f))
        values.add(BarEntry(8f, 30f))
        values.add(BarEntry(9f, 45f))
        values.add(BarEntry(10f, 55f))
        values.add(BarEntry(11f, 105f))

        val barDataSet = BarDataSet(values, "")
        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(barDataSet)

        val barData = BarData(dataSets)
        barData.setValueTextSize(10f)
        barData.barWidth = 0.5f

        chart.data = barData
    }


    override fun onNothingSelected() {
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
    }

}


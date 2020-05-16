# Covirapp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mBarChart = findViewById<PieChart>(R.id.chart)

        var personas : ArrayList<PieEntry> = arrayListOf()

        personas.add( PieEntry(2000000.0f, 3.0f) )
        personas.add( PieEntry(175000.0f, 7.0f) )

        var barDataSet : PieDataSet = PieDataSet(personas, "R - Infectados / V - Recuperados")
        var colors : MutableList<Int> = mutableListOf( Color.rgb(222, 64, 47), Color.rgb(96, 222, 47) )
        barDataSet.colors = colors

        var barData : PieData = PieData(barDataSet)


        mBarChart.visibility = View.VISIBLE
        mBarChart.animateY(5000)
        mBarChart.data = barData
        var description : Description = Description()
        description.text = "Personas por Covid-19"
        mBarChart.description = description
        mBarChart.invalidate()
    }

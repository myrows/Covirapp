package com.example.covirapp.models



data class NewRegionsResponse(
    val dates: Map<String, X20200526>,
    val metadata: Metadata,
    val total: Total,
    val updated_at: String
)
/*
data class Dates(
    val `2020-05-26`: X20200526
)
 */

data class X20200526(
    val countries: Countries,
    val info: Info
)

data class Countries(
    val Spain: Spain
)

data class Spain(
    val date: String,
    val id: String,
    val links: List<Link>,
    val name: String,
    val name_es: String,
    val name_it: String,
    val regions: List<Region>,
    val source: String,
    val today_confirmed: Int,
    val today_deaths: Int,
    val today_hospitalised_patients_with_symptoms: Int,
    val today_intensive_care: Int,
    val today_new_confirmed: Int,
    val today_new_deaths: Int,
    val today_new_hospitalised_patients_with_symptoms: Int,
    val today_new_intensive_care: Int,
    val today_new_open_cases: Int,
    val today_new_recovered: Int,
    val today_new_total_hospitalised_patients: Int,
    val today_open_cases: Int,
    val today_recovered: Int,
    val today_total_hospitalised_patients: Int,
    val today_vs_yesterday_confirmed: Double,
    val today_vs_yesterday_deaths: Double,
    val today_vs_yesterday_hospitalised_patients_with_symptoms: Any,
    val today_vs_yesterday_intensive_care: Double,
    val today_vs_yesterday_open_cases: Double,
    val today_vs_yesterday_recovered: Double,
    val today_vs_yesterday_total_hospitalised_patients: Double,
    val yesterday_confirmed: Int,
    val yesterday_deaths: Int,
    val yesterday_hospitalised_patients_with_symptoms: Int,
    val yesterday_intensive_care: Int,
    val yesterday_open_cases: Int,
    val yesterday_recovered: Int,
    val yesterday_total_hospitalised_patients: Int
)

data class Metadata(
    val `by`: String,
    val url: List<String>
)

data class Total(
    val date: String,
    val name: String,
    val name_es: String,
    val name_it: String,
    val rid: String,
    val source: String,
    val today_confirmed: Int,
    val today_deaths: Int,
    val today_new_confirmed: Int,
    val today_new_deaths: Int,
    val today_new_open_cases: Int,
    val today_new_recovered: Int,
    val today_open_cases: Int,
    val today_recovered: Int,
    val today_vs_yesterday_confirmed: Double,
    val today_vs_yesterday_deaths: Double,
    val today_vs_yesterday_open_cases: Double,
    val today_vs_yesterday_recovered: Double,
    val yesterday_confirmed: Int,
    val yesterday_deaths: Int,
    val yesterday_open_cases: Int,
    val yesterday_recovered: Int
)

data class Info(
    val date: String,
    val date_generation: String,
    val yesterday: String
)



data class Link(
    val href: String,
    val rel: String,
    val type: String
)

data class Region(
    val date: String,
    val id: String,
    val links: List<LinkX>,
    val name: String,
    val name_es: String,
    val name_it: String,
    val source: String,
    val sub_regions: List<SubRegion>,
    val today_confirmed: Int,
    val today_deaths: Int,
    val today_hospitalised_patients_with_symptoms: Int,
    val today_intensive_care: Int,
    val today_new_confirmed: Int,
    val today_new_deaths: Int,
    val today_new_hospitalised_patients_with_symptoms: Int,
    val today_new_intensive_care: Int,
    val today_new_open_cases: Int,
    val today_new_recovered: Int,
    val today_new_total_hospitalised_patients: Int,
    val today_open_cases: Int,
    val today_recovered: Int,
    val today_total_hospitalised_patients: Int,
    val today_vs_yesterday_confirmed: Double,
    val today_vs_yesterday_deaths: Double,
    val today_vs_yesterday_hospitalised_patients_with_symptoms: Any,
    val today_vs_yesterday_intensive_care: Double,
    val today_vs_yesterday_open_cases: Double,
    val today_vs_yesterday_recovered: Double,
    val today_vs_yesterday_total_hospitalised_patients: Double,
    val yesterday_confirmed: Int,
    val yesterday_deaths: Int,
    val yesterday_hospitalised_patients_with_symptoms: Int,
    val yesterday_intensive_care: Int,
    val yesterday_open_cases: Int,
    val yesterday_recovered: Int,
    val yesterday_total_hospitalised_patients: Int
)

data class LinkX(
    val href: String,
    val rel: String,
    val type: String
)

data class SubRegion(
    val date: String,
    val id: String,
    val name: String,
    val name_es: String,
    val name_it: String,
    val source: String,
    val today_confirmed: Int,
    val today_deaths: Int,
    val today_intensive_care: Int,
    val today_new_confirmed: Int,
    val today_new_deaths: Int,
    val today_new_intensive_care: Int,
    val today_new_recovered: Int,
    val today_new_total_hospitalised_patients: Int,
    val today_recovered: Int,
    val today_total_hospitalised_patients: Int,
    val today_vs_yesterday_confirmed: Double,
    val today_vs_yesterday_deaths: Double,
    val today_vs_yesterday_intensive_care: Double,
    val today_vs_yesterday_recovered: Any,
    val today_vs_yesterday_total_hospitalised_patients: Double,
    val yesterday_confirmed: Int,
    val yesterday_deaths: Int,
    val yesterday_intensive_care: Int,
    val yesterday_recovered: Int,
    val yesterday_total_hospitalised_patients: Int
)
package com.ddanddan.watch.data.response

//todo - 수정 예정
//@Serializable
//data class SearchResponse(
//    @SerialName("results")
//    val results: List<Result>?,
//    @SerialName("total")
//    val total: Int?,
//    @SerialName("total_pages")
//    val totalPages: Int?
//) {
//    @Serializable
//    data class Result(
//        @SerialName("id")
//        val id: String?,
//        @SerialName("description")
//        val description: String?,
//        @SerialName("urls")
//        val urls: Urls?,
//        @SerialName("user")
//        val user: User?
//    ) {
//        @Serializable
//        data class Urls(
//            @SerialName("full")
//            val full: String?,
//            @SerialName("raw")
//            val raw: String?,
//            @SerialName("regular")
//            val regular: String?,
//            @SerialName("small")
//            val small: String?,
//            @SerialName("small_s3")
//            val smallS3: String?,
//            @SerialName("thumb")
//            val thumb: String?
//        )
//
//        @Serializable
//        data class User(
//            @SerialName("name")
//            val name: String?,
//            @SerialName("location")
//            val location: String?
//        )
//    }
//}
//
//fun SearchResponse.toSearchResult(): List<SearchResult> {
//    return results?.map { result ->
//        SearchResult(
//            totalPages = totalPages ?: 1,
//            id = result.id ?: "",
//            imageUrls = ImageUrls(
//                full = result.urls?.full ?: "",
//                raw = result.urls?.raw ?: "",
//                regular = result.urls?.regular ?: "",
//                small = result.urls?.small ?: "",
//                smallS3 = result.urls?.smallS3 ?: "",
//                thumb = result.urls?.thumb ?: ""
//            ),
//            name = result.user?.name ?: "",
//            location = result.user?.location ?: "",
//            description = result.description ?: ""
//        )
//    } ?: emptyList()
//}
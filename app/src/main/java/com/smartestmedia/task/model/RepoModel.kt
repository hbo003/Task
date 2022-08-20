package com.smartestmedia.task.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class ResultApi : ArrayList<RepoModel>()

data class RepoModel(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("created_at")
    val createdAt: String? = " ",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("owner")
    val owner: Owner? = null,
    @SerializedName("stargazers_count")
    val stargazersCount: Int? = 0
) : Serializable

data class Owner(
    @SerializedName("avatar_url")
    val avatarUrl: String? = "",
)

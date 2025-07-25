package com.pidygb.mynasadailypics.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Footer(
    val id: String,
    val sections: List<FooterSection>,
    val socials: List<Social>,
    val copyright: String
)

@Serializable
data class FooterSection(
    val title: String,
    val links: List<FooterLink>
)

@Serializable
data class FooterLink(
    val text: String,
    val url: String
)

@Serializable
data class Social(
    val name: String,
    val url: String
)
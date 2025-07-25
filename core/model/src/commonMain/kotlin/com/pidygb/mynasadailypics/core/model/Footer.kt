package com.pidygb.mynasadailypics.core.model

data class Footer(
    val id: String,
    val sections: List<FooterSection>,
    val socials: List<Social>,
    val copyright: String
)

data class FooterSection(
    val title: String,
    val links: List<FooterLink>
)

data class FooterLink(
    val text: String,
    val url: String
)

data class Social(
    val name: String,
    val url: String
)
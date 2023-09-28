package com.example.domain.usecase

import com.example.domain.entity.Translations
import com.example.domain.repo.translationRepo
import javax.inject.Inject

class InsertrTranslation @Inject constructor(private val translationRepo: translationRepo) {
    suspend operator fun invoke(translations: Translations) = translationRepo.addTranslation(translations = translations)

}
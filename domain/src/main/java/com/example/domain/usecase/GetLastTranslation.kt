package com.example.domain.usecase

import com.example.domain.repo.translationRepo
import javax.inject.Inject

class GetLastTranslation @Inject constructor(private val translationRepo: translationRepo) {
    suspend operator fun invoke() = translationRepo.getLastTranslation()
}
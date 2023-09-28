package com.example.domain.usecase

import com.example.domain.repo.translationRepo
import javax.inject.Inject

class GetRandomTranslation @Inject constructor(private val translationRepo: translationRepo) {
    suspend operator fun invoke() = translationRepo.getRandomTranslation()
}
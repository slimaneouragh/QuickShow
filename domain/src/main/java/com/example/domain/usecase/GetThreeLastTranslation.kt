package com.example.domain.usecase

import com.example.domain.repo.translationRepo
import javax.inject.Inject

class GetThreeLastTranslation @Inject constructor(private val translationRepo : translationRepo) {
    suspend operator fun invoke() = translationRepo.getThreeLastTranslation()
}
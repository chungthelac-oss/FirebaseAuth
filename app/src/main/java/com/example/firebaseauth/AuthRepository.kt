package com.example.firebaseauth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    suspend fun login(email: String, password: String): Result<String> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Result.success(result.user?.uid ?: throw Exception("User not found"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(email: String, password: String): Result<String> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: throw Exception("User not found")

            // Tạo document user với role mặc định là "user"
            db.collection("users").document(uid).set(
                mapOf(
                    "email" to email,
                    "role" to "user"  // mặc định là user
                )
            ).await()

            Result.success(uid)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Lấy role của user hiện tại
    suspend fun getUserRole(): String {
        return try {
            val uid = auth.currentUser?.uid ?: return "user"
            val doc = db.collection("users").document(uid).get().await()
            doc.getString("role") ?: "user"
        } catch (e: Exception) {
            "user"
        }
    }

    fun logout() = auth.signOut()
    fun getCurrentUser() = auth.currentUser
}
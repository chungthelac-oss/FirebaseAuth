package com.example.firebaseauth.repository

import com.example.firebaseauth.entities.TinTuc
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class TinTucFirestoreRepository {
    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("tintuc")

    // Lấy tất cả tin tức realtime
    fun getAllTinTuc(): Flow<List<TinTuc>> = callbackFlow {
        val listener = collection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            val list = snapshot?.documents?.mapNotNull { doc ->
                TinTuc(
                    id = 0,
                    tieuDe = doc.getString("tieuDe") ?: "",
                    noiDung = doc.getString("noiDung") ?: "",
                    hinhAnh = doc.getString("hinhAnh") ?: "",
                    danhMucId = doc.getLong("danhMucId")?.toInt() ?: 0,
                    docId = doc.id  // ← lưu docId
                )
            } ?: emptyList()
            trySend(list)
        }
        awaitClose { listener.remove() }
    }

    // Thêm tin tức
    suspend fun insertTinTuc(tinTuc: TinTuc): Result<Unit> {
        return try {
            val data = hashMapOf(
                "tieuDe" to tinTuc.tieuDe,
                "noiDung" to tinTuc.noiDung,
                "hinhAnh" to tinTuc.hinhAnh,
                "danhMucId" to tinTuc.danhMucId,
                "createdAt" to System.currentTimeMillis()
            )
            collection.add(data).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Xóa tin tức
    suspend fun deleteTinTuc(docId: String): Result<Unit> {
        return try {
            collection.document(docId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
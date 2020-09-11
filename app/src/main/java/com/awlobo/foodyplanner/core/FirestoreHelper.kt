package com.awlobo.foodyplanner.core

import android.util.Log
import com.awlobo.foodyplanner.domain.Food
import com.awlobo.foodyplanner.domain.Planning
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreHelper() {

    var db = FirebaseFirestore.getInstance()

    fun addFood(data: Food) {
        db.collection("myApps")
            .document("FoodyPlanner")
            .collection("Foods")
            .document(data.name)
            .set(data, SetOptions.merge())
            .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }
    }


    fun addPlanning(data: Planning) {
        db.collection("myApps")
            .document("FoodyPlanner")
            .collection("Saved")
            .document(data.name)
            .set(data, SetOptions.merge())
            .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }
    }


    fun readFoodList(): CollectionReference {
        return db.collection("myApps")
            .document("FoodyPlanner")
            .collection("Foods")
    }

    fun readPlanning(): CollectionReference {
        return db.collection("myApps")
            .document("FoodyPlanner")
            .collection("Saved")
//            .document("plan1")
    }



    fun readDataOnce() {
        val users = db.collection("users")
        users.get()
            .addOnSuccessListener { result ->
                if (result != null) {
                    for (document in result) {
                        Log.d("TAG", "${document.id} => ${document.data}")
                    }
                } else {
                    Log.d("TAG", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
    }
}
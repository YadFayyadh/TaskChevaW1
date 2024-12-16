package org.example.app

fun main() {
    println("Pilih karakter anda!")
    println("1. Orang Jawa (damage besar, darah kecil, skill: Sound Horeg)")
    println("2. Orang Sunda (darah besar, tahan serangan, skill: Manipulasi)")
    println("3. Orang Batak (darah besar, tahan serangan, skill: Parang Splash)")
    print("Pilih jagoan anda: ")

    val pilih = readLine()?.toIntOrNull()

    val player = when (pilih) {
        1 -> karakter("Orang Jawa", hp = 100, damage = 30, tahanSerangan = 1.0, skill = Skill.SoundHoreg)
        2 -> karakter("Orang Sunda", hp = 150, damage = 15, tahanSerangan = 0.85, skill = Skill.Manipulasi)
        3 -> karakter("Orang Batak", hp = 150, damage = 20, tahanSerangan = 0.9, skill = Skill.ParangSplash)
        else -> {
            println("Pilihan tidak valid. Keluar dari game.")
            return
        }
    }

    println("\nPilih lawan anda:")
    println("1. Orang Jawa Evil (HP: 80, Damage: 10)")
    println("2. Bocil Kematian (HP: 150, Damage: 20)")
    println("3. SJW Twitter Feminis (HP: 200, Damage: 35)")
    print("Pilih lawan (1, 2, atau 3): ")

    val pilihLawan = readLine()?.toIntOrNull()

    val lawan = when (pilihLawan) {
        1 -> karakter("Orang Jawa Evil", hp = 80, damage = 10, tahanSerangan = 1.0)
        2 -> karakter("Bocil Kematian", hp = 150, damage = 20, tahanSerangan = 1.0)
        3 -> karakter("SJW Twitter Feminis", hp = 200, damage = 35, tahanSerangan = 1.0)
        else -> {
            println("Pilihan tidak valid. Keluar dari game.")
            return
        }
    }

    println("\nAnda memilih ${player.tipe}! Lawan anda adalah ${lawan.tipe}.")
    println("HP anda: ${player.hp}, Damage: ${player.damage}")
    println("HP lawan: ${lawan.hp}, Damage: ${lawan.damage}")

    var lawanSerangable = true
    var seranganCount = 0

    while (player.hp > 0 && lawan.hp > 0) {
        println("\nGiliran anda!")
        println("1. Serang lawan")
        println("2. Gunakan skill spesial (${player.skill.description})")
        println("3. Menangkis serangan (hanya setelah 1 kali serangan)")
        print("Pilih aksi (1, 2, atau 3): ")

        val aksiJagoan = readLine()?.toIntOrNull()

        if (aksiJagoan == 1) {
            lawan.takeDamage(player.damage)
            seranganCount++
            println("Anda memberikan ${player.damage} damage. HP lawan: ${lawan.hp}")
        } else if (aksiJagoan == 2) {
            when (player.skill) {
                Skill.SoundHoreg -> {
                    println("Anda menggunakan skill Sound Horeg! Lawan tidak bisa menyerang di giliran berikutnya.")
                    lawanSerangable = false
                    seranganCount++
                }
                Skill.Manipulasi -> {
                    println("Anda menggunakan skill Manipulasi! Lawan terkena serangan dirinya sendiri sebesar ${lawan.damage}.")
                    lawan.takeDamage(lawan.damage)
                    seranganCount++
                    println("HP lawan setelah terkena serangan: ${lawan.hp}")
                }
                Skill.ParangSplash -> {
                    val totalDamage = (player.damage * 1.2).toInt()
                    println("Anda menggunakan skill Parang Splash! Memberikan ${totalDamage} damage ke lawan.")
                    lawan.takeDamage(totalDamage)
                    seranganCount++
                    println("HP lawan setelah terkena serangan: ${lawan.hp}")
                }
                Skill.None -> TODO()
            }
        } else if (aksiJagoan == 3) {
            if (seranganCount >= 1) {
                println("Anda menggunakan menangkis! Serangan lawan berkurang.")
                lawanSerangable = false
                seranganCount = 0
            } else {
                println("Anda belum cukup menyerang untuk menangkis. Anda harus menyerang 3 kali berturut-turut.")
            }
        } else {
            println("Aksi tidak valid. Giliran anda dilewati.")
        }

        if (lawan.hp <= 0) {
            println("\nAnda mengalahkan ${lawan.tipe}. Selamat, anda menang!")
            break
        }

        if (lawanSerangable) {
            println("\nGiliran lawan menyerang!")
            player.takeDamage(lawan.damage)
            println("${lawan.tipe} memberikan ${lawan.damage} damage ke anda. HP anda: ${player.hp}")
        } else {
            println("\nLawan tidak bisa menyerang kali ini!")
            lawanSerangable = true
        }

        if (player.hp <= 0) {
            println("\nAnda dikalahkan oleh ${lawan.tipe}. Game over.")
            break
        }
    }
}

class karakter(
    val tipe: String,
    var hp: Int,
    val damage: Int,
    private val tahanSerangan: Double,
    val skill: Skill = Skill.None
) {
    fun takeDamage(serangan: Int) {
        val damageBerkurang = (serangan * tahanSerangan).toInt()
        hp -= damageBerkurang
    }
}

enum class Skill(val description: String) {
    SoundHoreg("Lawan tidak dapat menyerang selama 1 giliran"),
    Manipulasi("Lawan terkena serangan basic attack-nya sendiri"),
    ParangSplash("Lawan terkena 20% tambahan damage + basic attack"),
    None("Tidak ada skill spesial")
}

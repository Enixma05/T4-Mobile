# StudentContactApp

## Identitas

**Nama:** Fadlullah Hasan
**NIM:** F1D02310008

---

## Deskripsi Aplikasi

**StudentContactApp** adalah aplikasi Android yang digunakan untuk mengelola data kontak mahasiswa.
Pada aplikasi ini, pengguna dapat:

* Login ke dalam aplikasi
* Melihat daftar mahasiswa
* Menambahkan data mahasiswa baru
* Mengedit dan menghapus data mahasiswa
* Melihat detail mahasiswa
* Menyimpan catatan pribadi pada setiap mahasiswa
* Mengatur preferensi seperti dark mode dan notifikasi

---

## Screenshot Aplikasi

### Halaman Login

![login](screenshots/login.png)

### Halaman Home

![home](screenshots/home.png)

### Halaman Detail

![detail](screenshots/detail.png)

### Halaman Form Tambah

![form](screenshots/form.png)

---

## Metode Penyimpanan Data

### 1. Room Database
Room Database digunakan untuk menyimpan data utama mahasiswa seperti nama, NIM, prodi, email, dan semester. 
Metode ini dipilih karena mampu mengelola data secara terstruktur (relasional), mendukung operasi CRUD dengan efisien, serta terintegrasi dengan Kotlin melalui DAO dan Entity sehingga lebih aman dan mudah dikembangkan.

### 2. SharedPreferences
SharedPreferences digunakan untuk menyimpan data ringan seperti status login pengguna dan pengaturan aplikasi (dark mode, notifikasi, dan ukuran font). 
Metode ini dipilih karena sederhana, cepat diakses, dan tidak memerlukan struktur kompleks seperti database, sehingga cocok untuk data konfigurasi atau preferensi user.

### 3. Internal Storage (FileHelper)
Internal Storage digunakan untuk menyimpan catatan (note) setiap mahasiswa dalam bentuk file `.txt`. 
Pendekatan ini dipilih karena fleksibel untuk data berbentuk teks, tidak membebani database utama, serta memungkinkan penyimpanan terpisah berdasarkan identitas mahasiswa (NIM), sehingga lebih terorganisir.

---

## Kendala dan Solusi

### 1.Kurangnya pemahaman terhadap komponen layout Android
Dalam proses pengembangan, terdapat kesulitan dalam memahami dan menggunakan berbagai komponen UI (seperti CardView, RecyclerView, dan komponen Material Design) serta bagaimana mengatur layout agar tampil rapi dan responsif.
Solusi: Bertanya kepada teman untuk nama komponen serta memanfaatkan AI untuk memahami fungsi masing-masing komponen serta cara penggunaannya secara tepat.

### 2.Kesulitan dalam konfigurasi dan pengaturan Gradle
Permasalahan sering terjadi saat mengatur dependency, plugin, dan kompatibilitas versi Gradle yang menyebabkan error saat build aplikasi.
Solusi: Bertanya kepada dosen melalui grup mata kuliah serta memanfaatkan bantuan AI untuk menemukan solusi error dan konfigurasi yang sesuai.

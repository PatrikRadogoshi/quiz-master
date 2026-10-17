package com.quizmaster.app

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class MainActivity : Activity() {
    private val bg = Color.rgb(20, 22, 45)
    private val panel = Color.rgb(36, 39, 72)
    private val purple = Color.rgb(108, 99, 255)
    private val white = Color.WHITE
    private val muted = Color.rgb(195, 198, 224)
    private var language = "sq"
    private var category = "all"
    private var questions = listOf<Question>()
    private var index = 0
    private var score = 0
    private var answered = false
    private var timer: CountDownTimer? = null
    private lateinit var root: LinearLayout
    private lateinit var content: LinearLayout
    private lateinit var timerText: TextView

    data class Question(val category: String, val sq: String, val en: String, val optionsSq: List<String>, val optionsEn: List<String>, val correct: Int)

    private val bank = listOf(
        Question("science", "Cili është planeti më i madh?", "Which is the largest planet?", listOf("Toka", "Marsi", "Jupiteri", "Venusi"), listOf("Earth", "Mars", "Jupiter", "Venus"), 2),
        Question("geography", "Cili është kryeqyteti i Francës?", "What is the capital of France?", listOf("Roma", "Parisi", "Madridi", "Lisboa"), listOf("Rome", "Paris", "Madrid", "Lisbon"), 1),
        Question("sport", "Sa lojtarë ka një ekip futbolli në fushë?", "How many players are on a football team on the field?", listOf("9", "10", "11", "12"), listOf("9", "10", "11", "12"), 2),
        Question("history", "Cila strukturë e lashtë ndodhet në Egjipt?", "Which ancient structure is in Egypt?", listOf("Koloseu", "Piramidat", "Parthenoni", "Stonehenge"), listOf("Colosseum", "Pyramids", "Parthenon", "Stonehenge"), 1),
        Question("science", "Sa këmbë ka merimanga?", "How many legs does a spider have?", listOf("6", "8", "10", "12"), listOf("6", "8", "10", "12"), 1),
        Question("culture", "Sa ditë ka një vit i zakonshëm?", "How many days are in a common year?", listOf("364", "365", "366", "360"), listOf("364", "365", "366", "360"), 1),
        Question("geography", "Cili është oqeani më i madh?", "Which is the largest ocean?", listOf("Atlantiku", "Indian", "Arktiku", "Paqësori"), listOf("Atlantic", "Indian", "Arctic", "Pacific"), 3),
        Question("technology", "Çfarë do të thotë CPU?", "What does CPU stand for?", listOf("Central Processing Unit", "Computer Power Utility", "Core Program Unit", "Central Print Unit"), listOf("Central Processing Unit", "Computer Power Utility", "Core Program Unit", "Central Print Unit"), 0),
        Question("sport", "Sa unaza ka simboli olimpik?", "How many rings are in the Olympic symbol?", listOf("4", "5", "6", "7"), listOf("4", "5", "6", "7"), 1),
        Question("science", "Cili gaz thithin bimët nga ajri?", "Which gas do plants absorb from the air?", listOf("Oksigjen", "Helium", "Dioksid karboni", "Hidrogjen"), listOf("Oxygen", "Helium", "Carbon dioxide", "Hydrogen"), 2),
        Question("culture", "Sa muaj ka një vit?", "How many months are in a year?", listOf("10", "11", "12", "13"), listOf("10", "11", "12", "13"), 2),
        Question("history", "Në cilin vend filluan Lojërat Olimpike antike?", "In which country did the ancient Olympic Games begin?", listOf("Itali", "Greqi", "Egjipt", "Turqi"), listOf("Italy", "Greece", "Egypt", "Turkey"), 1),
        Question("technology", "Cila pajisje përdoret për të lëvizur kursorin?", "Which device is used to move the cursor?", listOf("Printeri", "Miu", "Mikrofoni", "Altoparlanti"), listOf("Printer", "Mouse", "Microphone", "Speaker"), 1),
        Question("geography", "Në cilin kontinent ndodhet Kosova?", "On which continent is Kosovo?", listOf("Azia", "Afrika", "Evropa", "Amerika"), listOf("Asia", "Africa", "Europe", "America"), 2),
        Question("science", "Cila është ylli më i afërt me Tokën?", "What is the closest star to Earth?", listOf("Siriusi", "Dielli", "Polaris", "Vega"), listOf("Sirius", "The Sun", "Polaris", "Vega"), 1),
        Question("sport", "Në cilin sport përdoret raketa?", "In which sport is a racket used?", listOf("Tenis", "Futboll", "Not", "Vrapim"), listOf("Tennis", "Football", "Swimming", "Running"), 0),
        Question("culture", "Cila ngjyrë krijohet nga e kuqja dhe e verdha?", "Which color is made by mixing red and yellow?", listOf("Vjollcë", "Portokalli", "E gjelbër", "Blu"), listOf("Purple", "Orange", "Green", "Blue"), 1),
        Question("history", "Kush ishte i pari që shkeli në Hënë?", "Who was the first person to walk on the Moon?", listOf("Buzz Aldrin", "Neil Armstrong", "Yuri Gagarin", "John Glenn"), listOf("Buzz Aldrin", "Neil Armstrong", "Yuri Gagarin", "John Glenn"), 1),
        Question("technology", "Cila prej këtyre është shfletues interneti?", "Which of these is a web browser?", listOf("Chrome", "Excel", "Paint", "Calculator"), listOf("Chrome", "Excel", "Paint", "Calculator"), 0),
        Question("geography", "Cili është kryeqyteti i Shqipërisë?", "What is the capital of Albania?", listOf("Prizreni", "Shkupi", "Tirana", "Durrësi"), listOf("Prizren", "Skopje", "Tirana", "Durrës"), 2)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showHome()
    }

    private fun text(value: String, size: Float, color: Int = white, bold: Boolean = false): TextView = TextView(this).apply {
        text = value; textSize = size; setTextColor(color); gravity = Gravity.CENTER
        if (bold) setTypeface(null, Typeface.BOLD)
        setPadding(12, 10, 12, 10)
    }

    private fun button(label: String, color: Int = panel, onClick: () -> Unit): Button = Button(this).apply {
        text = label; textSize = 15f; setTextColor(white); isAllCaps = false
        background = shape(color, 18)
        setPadding(12, 12, 12, 12)
        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply { setMargins(0, 7, 0, 7) }
        setOnClickListener { onClick() }
    }

    private fun shape(color: Int, radius: Int) = GradientDrawable().apply { setColor(color); cornerRadius = radius.toFloat() }

    private fun shell(): LinearLayout {
        root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setBackgroundColor(bg); setPadding(22, 18, 22, 18) }
        val scroll = ScrollView(this)
        content = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; gravity = Gravity.CENTER_HORIZONTAL }
        scroll.addView(content)
        root.addView(scroll, LinearLayout.LayoutParams(-1, -1))
        setContentView(root)
        return content
    }

    private fun addSpace(height: Int = 10) { content.addView(View(this), LinearLayout.LayoutParams(1, height)) }
    private fun addText(value: String, size: Float, color: Int = white, bold: Boolean = false) { content.addView(text(value, size, color, bold), LinearLayout.LayoutParams(-1, -2)) }
    private fun t(sq: String, en: String) = if (language == "sq") sq else en

    private fun showHome() {
        timer?.cancel()
        shell()
        addSpace(28)
        addText("🧠 QUIZ MASTER", 30f, white, true)
        addText(t("Sa i zgjuar je? Testoje veten!", "How smart are you? Test yourself!"), 16f, muted)
        addSpace(24)
        content.addView(button(if (language == "sq") "🌐  Shqip  |  English" else "🌐  English  |  Shqip", purple) {
            language = if (language == "sq") "en" else "sq"; showHome()
        })
        addSpace(12)
        addText(t("Zgjidh kategorinë", "Choose a category"), 20f, white, true)
        val cats = listOf("all" to t("Të gjitha kategoritë", "All categories"), "science" to t("Shkencë", "Science"), "geography" to t("Gjeografi", "Geography"), "sport" to t("Sport", "Sports"), "history" to t("Histori", "History"), "technology" to t("Teknologji", "Technology"), "culture" to t("Kulturë e përgjithshme", "General knowledge"))
        cats.forEach { (id, label) -> content.addView(button((if (category == id) "✓  " else "   ") + label, if (category == id) purple else panel) { category = id; showHome() }) }
        addSpace(14)
        content.addView(button(t("LUJ TANI  →", "PLAY NOW  →"), Color.rgb(40, 175, 125)) { startGame() })
        addSpace(8)
        addText(t("10 pyetje · 15 sekonda për pyetje · 100 pikë për përgjigje të saktë", "10 questions · 15 seconds per question · 100 points per correct answer"), 12f, muted)
        addSpace(18)
        addText(t("Versioni 1.0 · Solo offline", "Version 1.0 · Offline solo"), 12f, muted)
    }

    private fun startGame() {
        questions = (if (category == "all") bank else bank.filter { it.category == category }).shuffled().take(10)
        if (questions.isEmpty()) questions = bank.shuffled().take(10)
        index = 0; score = 0
        showQuestion()
    }

    private fun showQuestion() {
        timer?.cancel()
        answered = false
        shell()
        val q = questions[index]
        addText("QUIZ MASTER", 20f, muted, true)
        addSpace(8)
        addText(t("Pyetja ${index + 1} nga ${questions.size}", "Question ${index + 1} of ${questions.size}"), 15f, muted)
        addText("⭐ $score", 22f, Color.rgb(255, 215, 90), true)
        timerText = text("15 s", 18f, Color.rgb(255, 190, 90), true)
        content.addView(timerText, LinearLayout.LayoutParams(-1, -2))
        addSpace(16)
        val prompt = if (language == "sq") q.sq else q.en
        val questionCard = text(prompt, 23f, white, true).apply { background = shape(panel, 22); setPadding(18, 28, 18, 28) }
        content.addView(questionCard, LinearLayout.LayoutParams(-1, -2))
        addSpace(12)
        val options = if (language == "sq") q.optionsSq else q.optionsEn
        options.forEachIndexed { i, option -> content.addView(button("${('A' + i)}.  $option", panel) { choose(i) }) }
        addSpace(8)
        addText(t("Përgjigjja e saktë jep 100 pikë", "Correct answer earns 100 points"), 13f, muted)
        timer = object : CountDownTimer(15000, 1000) {
            override fun onTick(ms: Long) { if (!answered) timerText.text = "${(ms / 1000) + 1} s" }
            override fun onFinish() { if (!answered) { answered = true; reveal(null) } }
        }.start()
    }

    private fun choose(choice: Int) {
        if (answered) return
        answered = true; timer?.cancel()
        if (choice == questions[index].correct) score += 100
        reveal(choice)
    }

    private fun reveal(choice: Int?) {
        val q = questions[index]
        val correct = q.correct
        val options = if (language == "sq") q.optionsSq else q.optionsEn
        shell()
        addSpace(30)
        addText(if (choice == correct) "🎉" else "💡", 46f)
        addText(if (choice == correct) t("Saktë! +100 pikë", "Correct! +100 points") else t("Përgjigjja e saktë:", "The correct answer:"), 23f, if (choice == correct) Color.rgb(90, 225, 160) else Color.rgb(255, 190, 90), true)
        addText(options[correct], 22f, white, true)
        addSpace(18)
        addText("⭐ $score", 26f, Color.rgb(255, 215, 90), true)
        addSpace(18)
        content.addView(button(if (index < questions.lastIndex) t("Pyetja tjetër  →", "Next question  →") else t("Shiko rezultatin  →", "See results  →"), purple) {
            if (index < questions.lastIndex) { index++; showQuestion() } else showResults()
        })
    }

    private fun showResults() {
        shell()
        addSpace(42)
        addText("🏆", 52f)
        addText(t("Loja përfundoi!", "Quiz complete!"), 28f, white, true)
        addSpace(12)
        addText("$score / ${questions.size * 100}", 34f, Color.rgb(255, 215, 90), true)
        val right = score / 100
        addText(t("Përgjigje të sakta: $right nga ${questions.size}", "Correct answers: $right out of ${questions.size}"), 17f, muted)
        addSpace(26)
        content.addView(button(t("Luaj përsëri", "Play again"), purple) { startGame() })
        content.addView(button(t("Kthehu në menu", "Back to home"), panel) { showHome() })
        addSpace(16)
        addText(t("Më shumë kategori dhe loja online do të shtohen në versionet e ardhshme.", "More categories and online play will be added in future versions."), 13f, muted)
    }

    override fun onDestroy() { timer?.cancel(); super.onDestroy() }
}

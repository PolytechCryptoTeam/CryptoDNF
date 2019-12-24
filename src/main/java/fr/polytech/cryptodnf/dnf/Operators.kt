package fr.polytech.cryptodnf.dnf

enum class Operators(val op: String) {
    AND("∧"),
    OR("∨"),
    NOT("¬"),

    XOR("⊻"),
    XNOR("↔"),
    IMPLY("→"),
}
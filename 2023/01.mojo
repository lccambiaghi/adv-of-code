from python import Python
from python.object import PythonObject

fn part_one(lines: DynamicVector[String]) raises:
    var res = 0
    for line_index in range(len(lines)):
        let line = lines[line_index]
        var digits = String("")
        for char_index in range(len(line)):
            if isdigit(ord(line[char_index])):
                digits += line[char_index]
        digits = digits[0] + digits[len(digits)-1]
        res += atol(digits)
    print(res)

fn get_words() -> DynamicVector[String]:
    var WORDS = DynamicVector[String](9)
    WORDS.append("one")
    WORDS.append("two")
    WORDS.append("three")
    WORDS.append("four")
    WORDS.append("five")
    WORDS.append("six")
    WORDS.append("seven")
    WORDS.append("eight")
    WORDS.append("nine")
    return WORDS

fn get_digits() -> DynamicVector[String]:
    var DIGITS = DynamicVector[String](9)
    DIGITS.append("1")
    DIGITS.append("2")
    DIGITS.append("3")
    DIGITS.append("4")
    DIGITS.append("5")
    DIGITS.append("6")
    DIGITS.append("7")
    DIGITS.append("8")
    DIGITS.append("9")
    return DIGITS


fn find_first_digit(line: String, WORDS: DynamicVector[String], DIGITS: DynamicVector[String]) raises -> Int:
    var substr_best_match = 1000
    var first_digit: Int = -1

    for digit_ix in range(len(DIGITS)):
        let word = WORDS[digit_ix]
        let digit = DIGITS[digit_ix]

        var substr_match = line.find(word)
        if substr_match >= 0 and substr_match < substr_best_match:
            substr_best_match = substr_match
            first_digit = atol(digit_ix) + 1

        substr_match = line.find(digit)
        if substr_match >= 0 and substr_match < substr_best_match:
            substr_best_match = substr_match
            first_digit = atol(digit_ix) + 1

    return first_digit

fn find_last_digit(line: String, WORDS: DynamicVector[String], DIGITS: DynamicVector[String]) raises -> Int:
    var substr_best_match = -1
    var last_digit: Int = -1

    for digit_ix in range(len(DIGITS)):
        let word = WORDS[digit_ix]
        let digit = DIGITS[digit_ix]

        var substr_match = line.rfind(word)
        if substr_match >= 0 and substr_match > substr_best_match:
            substr_best_match = substr_match
            last_digit = atol(digit_ix) + 1

        substr_match = line.rfind(digit)
        if substr_match >= 0 and substr_match > substr_best_match:
            substr_best_match = substr_match
            last_digit = atol(digit_ix) + 1

    return last_digit

    

fn part_two(lines: DynamicVector[String]) raises:
    let WORDS = get_words()
    let DIGITS = get_digits()

    var res = 0
    for line_index in range(len(lines)):
        let line = lines[line_index]
        let first_digit = find_first_digit(line, WORDS, DIGITS)
        let last_digit = find_last_digit(line, WORDS, DIGITS)
        res += first_digit * 10 + last_digit
    print(res)
    

fn main() raises:
    with open("data/01.txt", "r") as f:
        let lines = f.read().split("\n")
        # part_one(lines)
        part_two(lines)

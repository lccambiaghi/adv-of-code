def part_one(lines: DynamicVector[String]):
    res = 0
    for line_ix in range(len(lines)):
        line = lines[line_ix]

        game_id = line.split(":")[0].split(" ")[1]
        draws = line.split(":")[1].split(";")
        if is_game_valid(draws):
            res += atol(game_id)
    print(res)


def is_game_valid(draws: DynamicVector[String]):
    for draw_ix in range(len(draws)):
        draw = draws[draw_ix]
        configurations = draw.split(",")

        for config_ix in range(len(configurations)):
            amount = atol(configurations[config_ix].split(" ")[1])
            color = configurations[config_ix].split(" ")[2]

            if color == "red" and amount > 12:
                return False
            if color == "green" and amount > 13:
                return False
            if color == "blue" and amount > 14:
                return False

    return True


def get_game_power(draws: DynamicVector[String]) -> Int:
    min_red = 1
    min_blue = 1
    min_green = 1

    for draw_ix in range(len(draws)):
        draw = draws[draw_ix]
        configurations = draw.split(",")

        for config_ix in range(len(configurations)):
            amount = atol(configurations[config_ix].split(" ")[1])
            color = configurations[config_ix].split(" ")[2]

            if color == "red" and amount > min_red:
                min_red = amount
            if color == "green" and amount > min_green:
                min_green = amount
            if color == "blue" and amount > min_blue:
                min_blue = amount

    return min_red * min_green * min_blue


def part_two(lines: DynamicVector[String]):
    res = 0
    for line_ix in range(len(lines)):
        line = lines[line_ix]

        game_id = line.split(":")[0].split(" ")[1]
        draws = line.split(":")[1].split(";")
        res += get_game_power(draws)
    print(res)



def main():
    with open("2023/data/02.txt", "r") as f:
        lines = f.read().split("\n")
        # part_one(lines)
        part_two(lines)

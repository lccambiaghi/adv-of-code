use std::{env, fs};
mod day_one;


fn main() {
		let args: Vec<String> = env::args().collect();

    let file_path = &args[1];
		let contents = fs::read_to_string(file_path).expect("could not read file!");

		let answer = day_one::compute_day_one_answer(contents);
		println!("{answer}");
		
}

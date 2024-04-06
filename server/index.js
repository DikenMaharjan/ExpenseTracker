const express = require("express");
const app = express();
const PORT = 4000;

app.use(express.urlencoded({ extended: true }));
app.use(express.json());

const users = [];
let userOtpCodes = {};

const generateID = () => Math.random().toString(36).substring(2, 10);
const generateCode = ()=> {
	const characters = '0123456789';
	const charactersLength = characters.length;
	let otp = '';
	for (let i = 0; i < 6; i++) {
	  otp += characters.charAt(Math.floor(Math.random() * charactersLength));
	}
	return otp;
  }
  


app.post("/api/signIn", (req, res) => {
	const { email, password } = req.body;
	let result = users.filter(
		(user) => user.email === email && user.password === password
	);

	if (result.length !== 1) {
		return res.status(401).send({
			error_message: "Incorrect Credentials"
		});
	}
	code = generateCode();
	
	console.log(`OTP sent to email: ${email}`);
	console.log(`Generated code is ${code}`);

	let user = result[0];

	res.json({
			username: user.username,
			id:user.id,
			requiresVerification: true
		});
});

app.post("/api/signUp", (req, res) => {
	const { email, password, username } = req.body;
	let result = users.filter((user) => user.email === email);
	if (result.length === 0) {
		const newUser = { id: generateID(), email, password, username };
		users.push(newUser);
		let code = generateCode();
		userOtpCodes[newUser.id] = code;
		console.log(`OTP sent to email ${email}`);
		console.log(`The OTP is ${code}rs`)
		return res.json({
			message: "Account created successfully!",
			userId: newUser.id,
			email: email
		});
	}
	res.status(409).send({
		error_message: "User already exists",
	});
});

app.post("/api/verification", (req, res) => { 
	if (code === req.body.code) {
		return res.json({ message: "You're verified successfully" });
	}
	res.json({
		error_message: "Incorrect credentials",
	});
});

app.listen(PORT, () => {
	console.log(`Server listening on ${PORT}`);
});
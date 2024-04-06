const express = require("express");
const app = express();
const PORT = 4000;

app.use(express.urlencoded({ extended: true }));
app.use(express.json());

const users = [];
let code;

const generateID = () => Math.random().toString(36).substring(2, 10);
const generateCode = () => Math.random().toString(36).substring(2, 12);

const sendNovuNotification = async (recipient, verificationCode) => {
	try {
		console.log(verificationCode);
	} catch (err) {
		console.error(err);
	}
};

app.post("/api/login", (req, res) => {
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
	console.log("Telephone Number", result[0].tel);
	console.log(`Generated code is ${code}`);

	let user = result[0];

	res.json({
			username: user.username,
			id:user.id,
			requiresVerification: true
		});
});

app.post("/api/register", (req, res) => {
	const { email, password, tel, username } = req.body;
	let result = users.filter((user) => user.email === email || user.tel === tel);
	if (result.length === 0) {
		const newUser = { id: generateID(), email, password, username, tel };
		users.push(newUser);
		return res.json({
			message: "Account created successfully!",
		});
	}
	res.json({
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
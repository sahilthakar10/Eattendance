class ErrorHandler extends Error {
    constructor(statusCode, message) {
        super();
        this.statusCode = statusCode;
        this.message = message;
    }
}

const handleError = (err, res) => {
    const { statusCode, message } = err;
    console.log(err);
    res.json(message);
};

module.exports = {
    ErrorHandler,
    handleError
};

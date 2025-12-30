package rivacore

import "time"

// Version returns the library version
func Version() string {
	return "1.0.0"
}

// Greet returns a greeting message
func Greet(name string) string {
	return "Hello, " + name + "!"
}

// Add returns sum of two integers
func Add(a, b int) int {
	return a + b
}

// Fibonacci returns the nth Fibonacci number
func Fibonacci(n int) int64 {
	if n <= 0 {
		return 0
	}
	if n == 1 {
		return 1
	}
	var a, b int64 = 0, 1
	for i := 2; i <= n; i++ {
		a, b = b, a+b
	}
	return b
}

// GetCurrentTime returns current time as formatted string
func GetCurrentTime() string {
	return time.Now().Format("2006-01-02 15:04:05")
}
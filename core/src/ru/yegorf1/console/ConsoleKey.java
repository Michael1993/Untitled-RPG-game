package ru.yegorf1.console;

public class ConsoleKey {
	private static final int CTRL_MODIFER = 1 << 0;
	private static final int LEFT_CTRL_MODIFER = 1 << 1;
	private static final int RIGHT_CTRL_MODIFER = 1 << 2;
	private static final int SHIFT_MODIFER = 1 << 3;
	
	private int modifers;
	public char character;
	
	public ConsoleKey (char character) {
		this.character = character;
		this.modifers = getModifers();
	}
	
	/**
	 * Returns was control pressed when key was typed
	 * @return was control pressed
	 */
	public boolean isControlPressed() {
		return (modifers & CTRL_MODIFER) == CTRL_MODIFER;
	}
	
	/**
	 * Returns was left control pressed when key was typed
	 * @return was control pressed
	 */
	public boolean isLeftControlPressed() {
		return (modifers & LEFT_CTRL_MODIFER) == LEFT_CTRL_MODIFER;
	}
	
	/**
	 * Returns was right control pressed when key was typed
	 * @return was control pressed
	 */
	public boolean isRightControlPressed() {
		return (modifers & RIGHT_CTRL_MODIFER) == RIGHT_CTRL_MODIFER;
	}
	
	/**
	 * Returns was shift pressed when key was typed
	 * @return was shift pressed
	 */
	public boolean isShiftPressed() {
		return (modifers & SHIFT_MODIFER) == SHIFT_MODIFER;
	}	
	
	private static int getModifers() {
		boolean isControlPressed = Console.isLeftCtrlPressed || Console.isRightCtrlPressed;
		
		int modifers = 0;

		modifers |= (isControlPressed? Integer.MAX_VALUE : 0) & CTRL_MODIFER;
		modifers |= (Console.isLeftCtrlPressed? Integer.MAX_VALUE : 0) & LEFT_CTRL_MODIFER;
		modifers |= (Console.isRightCtrlPressed? Integer.MAX_VALUE : 0) & RIGHT_CTRL_MODIFER;
		modifers |= (Console.isShiftPressed? Integer.MAX_VALUE : 0) & SHIFT_MODIFER;
		
		return modifers;
	}	
}
